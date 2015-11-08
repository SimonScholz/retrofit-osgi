package de.simonscholz.retrofit.app.job;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.di.UISynchronize;

import de.simonscholz.retrofit.model.Contributor;
import de.simonscholz.retrofit.model.GitHub;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;



public class JsonContributorsJob extends Job {
	
	public static final String API_URL = "https://api.github.com";
	
	private Consumer<List<Contributor>> contributorConsumer;

	private UISynchronize uiSynchronize;

	public JsonContributorsJob(Consumer<List<Contributor>> contributorConsumer, UISynchronize uiSynchronize) {
		super("Fetch Eclipse Contributors from Eclipse Platform");
		this.contributorConsumer = contributorConsumer;
		this.uiSynchronize = uiSynchronize;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		// Create a very simple REST adapter which points the GitHub API.
		Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create())
				.build();

		// Create an instance of our GitHub API interface.
		GitHub github = retrofit.create(GitHub.class);

		// Create a call instance for looking up Eclipse platform.ui contributors.
		Call<List<Contributor>> call = github.contributors("eclipse", "eclipse.platform.ui");

		try {
			List<Contributor> contributors = call.execute().body();
			uiSynchronize.asyncExec(new Runnable() {
				
				@Override
				public void run() {
					// pass the list of contributors to the consumer
					contributorConsumer.accept(contributors);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Status.OK_STATUS;
	}

}