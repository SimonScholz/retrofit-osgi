package de.simonscholz.retrofit.app.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.simonscholz.retrofit.app.job.JsonContributorsJob;
import de.simonscholz.retrofit.model.Contributor;

public class RetrofitSamplePart {
	
	@Inject
	private UISynchronize uiSynchronize;
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		TableViewer tableViewer = new TableViewer(parent);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		
		TableViewerColumn nameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		nameColumn.getColumn().setWidth(200);
		nameColumn.getColumn().setText("Name");
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if(element instanceof Contributor) {
					return ((Contributor) element).login;
				}
				
				return super.getText(element);
			}
		});
		
		TableViewerColumn contributionsColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		contributionsColumn.getColumn().setWidth(200);
		contributionsColumn.getColumn().setText("Contributions");
		contributionsColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if(element instanceof Contributor) {
					return Integer.toString(((Contributor) element).contributions);
				}
				
				return super.getText(element);
			}
		});
		
		JsonContributorsJob jsonContributorsJob = new JsonContributorsJob(tableViewer::setInput, uiSynchronize);
		jsonContributorsJob.schedule();
	}
	
}
