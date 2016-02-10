package com.epam.periodicals.client;

import com.epam.periodicals.shared.Journal;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dev.resource.impl.PathPrefix.Judgement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import javafx.scene.control.cell.CheckBoxTableCell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;




public class Periodicals implements EntryPoint {

	private final PeriodicalServiceAsync periodicalService = GWT.create(PeriodicalService.class);

	
	
	TabLayoutPanel mainPanel = new TabLayoutPanel(1.5, Unit.EM);
	
	private VerticalPanel logPanel = new VerticalPanel();
	private VerticalPanel journalListPanel = new VerticalPanel();
	private VerticalPanel myJournalListPanel = new VerticalPanel();
	
	private Button signinButton = new Button("Sign in");
	private Button signinButton2 = new Button("Sign in");
	private Button regButton = new Button("Register");
	private Button regButton2 = new Button("Register");
	private Button signout = new Button("Sign out");
	private TextBox login = new TextBox();
	private TextBox login2 = new TextBox();
	private TextBox sum = new TextBox();
	private PasswordTextBox pwd = new PasswordTextBox();
	private PasswordTextBox pwd2 = new PasswordTextBox();
	private PasswordTextBox pwd3 = new PasswordTextBox();
	private Label text1Label = new Label();
	private Label text2Label = new Label();
	private Label text3Label = new Label();
	private Label text4Label = new Label();
	private Label errorLabel = new Label();
	
	private FlexTable listFlexTable = new FlexTable();
	private FlexTable listFlexTable2 = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox journalNameTextBox = new TextBox();
	private TextBox journalDescriptionTextBox = new TextBox();
	private TextBox journalPriceTextBox = new TextBox();
	private Button addListButton = new Button("Add");
	private Button saveAddedJournalsButton = new Button("Save changes");
	private Label inviteToEditLabel = new Label();
	
	
	private ArrayList<Journal> listOfJournals = new ArrayList<>();
	private ArrayList<Journal> listOfMyJournals = new ArrayList<>();
	private List<Long> selectedIds = new ArrayList<>();
	private int rawsAdded;
	private Long sumToPay;
	
	public void onModuleLoad() {
		
		// add the log panel
		logPanel.add(text1Label);
		text1Label.setText("Enter your login:");
		logPanel.add(login);
		logPanel.add(text2Label);
		text2Label.setText("Enter your password:");
		logPanel.add(pwd);
		logPanel.add(signinButton);
		logPanel.add(regButton);
		logPanel.add(errorLabel);
		errorLabel.addStyleName("serverResponseLabelError");
		mainPanel.add(logPanel, "Sign in");

		// add handlers to sign in or move to the registration form
		final SigninHandler signinhandler = new SigninHandler();
		signinButton.addClickHandler(signinhandler);
		login.addKeyUpHandler(signinhandler);
		pwd.addKeyUpHandler(signinhandler);
		LoadRegForm loadregform = new LoadRegForm();
		regButton.addClickHandler(loadregform);
		
		// add the journal list panel
	    journalListPanel.add(listFlexTable);
	    mainPanel.add(journalListPanel, "List of Journals");
	    
		mainPanel.setHeight("1000px");
		RootPanel.get("main").add(mainPanel);
		mainPanel.selectTab(0);
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand () {
			public void execute () {
				login.setFocus(true);
			}
		});

		// load list of journals
		loadListOfJournals();
		
	}
	
	
	// load list of journals
	public void loadListOfJournals() {
		
		listOfJournals.clear();
		listFlexTable.removeAllRows();
		rawsAdded = 0;
		listFlexTable.setText(0, 0, "Journal");
	    listFlexTable.setText(0, 1, "Description");
	    listFlexTable.setText(0, 2, "Price (RUB)");
	    listFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
	    listFlexTable.addStyleName("watchList");
	    listFlexTable.setCellPadding(6);
	    listFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
	    listFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
	    
		periodicalService.loadData(new AsyncCallback<List<Journal>>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<Journal> result) {
				
				for (Journal journal : result) {
					
					final Journal displayJournal = journal;
					int row = listFlexTable.getRowCount();
					listOfJournals.add(displayJournal);
		    	    listFlexTable.setText(row, 0, journal.getName());
		    	    listFlexTable.setText(row, 1, journal.getDescription());
		    	    listFlexTable.setText(row, 2, String.valueOf(journal.getPrice()));
				}
				
			}
		});
		
	}
	
	// sign in service
	public void sendNameAndPwdToServer(boolean isNew, final String nameToServer, String pwdToServer) {
		
		if (!nameToServer.isEmpty() && !pwdToServer.isEmpty()) {
		
			periodicalService.loginServer(isNew, nameToServer, pwdToServer, new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					errorLabel.setText("Incorrect name or password. Please try again");
					signinButton.setEnabled(true);
					regButton2.setEnabled(true);
				}

				public void onSuccess(String result) {
					
					logPanel.clear();
					text4Label.setText(result);
					logPanel.add(text4Label);
					logPanel.add(signout);
					
					if (nameToServer == "admin") {
						adminSigned();
					} else {
						userSigned();
					}

				}
			});
		} else {
			errorLabel.setText("Some fields are empty");
			signinButton.setEnabled(true);
			regButton2.setEnabled(true);
		}
	}
	
	// load data for signed user
	public void userSigned() {
		
		
		for (int i = 1; i < listFlexTable.getRowCount(); i++) {
			final Long id = listOfJournals.get(i-1).getId();
			final Button addButton = new Button("Add");
			addButton.addStyleDependentName("remove");
			addButton.addClickHandler(new ClickHandler() {
    	      public void onClick(ClickEvent event) {
    	    	  addButton.setEnabled(false);
    	    	  periodicalService.addMyJournal(id, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
					}
// TODO
					@Override
					public void onSuccess(Void result) {
						loadListOfMyJournals();
						loadSumToPay();
						loadListOfPaidJournals();
					}
				});
    	      }
    	    });
    	    listFlexTable.setWidget(i, 3, addButton);
		}
			
		myJournalListPanel.add(listFlexTable2);
		mainPanel.add(myJournalListPanel, "My List");
		
		loadListOfMyJournals();
		loadSumToPay();
		loadListOfPaidJournals();
	}

	

	// load list of my journals
	private void loadListOfMyJournals() {
			
		listOfMyJournals.clear();
		listFlexTable2.removeAllRows();
		
		listFlexTable2.setText(0, 0, "Journal");
	    listFlexTable2.setText(0, 1, "Description");
	    listFlexTable2.setText(0, 2, "Price (RUB)");
	    listFlexTable2.getRowFormatter().addStyleName(0, "watchListHeader");
	    listFlexTable2.addStyleName("watchList");
	    listFlexTable2.setCellPadding(6);
	    listFlexTable2.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
	    listFlexTable2.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
		
		periodicalService.selectedIDs(new AsyncCallback<List<Long>>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<Long> result) {
				for (Long ids : result) {
					for (Journal list : listOfJournals) {
						if (list.getId().equals(ids)) {
							listOfMyJournals.add(list);
							Button addedButton = new Button("Added");
							addedButton.setEnabled(false);
							listFlexTable.setWidget(listOfJournals.indexOf(list)+1, 3, addedButton);
							int row = listFlexTable2.getRowCount();
				    	    listFlexTable2.setText(row, 0, list.getName());
				    	    listFlexTable2.setText(row, 1, list.getDescription());
				    	    listFlexTable2.setText(row, 2, String.valueOf(list.getPrice()));
						}
						
					}
				}
			}
		});
		
		
	}


	private void loadSumToPay() {
		
		periodicalService.sumToPay(new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}
			@Override
			public void onSuccess(Long result) {
				sumToPay = result;
				sum.setText("Total sum to pay: " + String.valueOf(sumToPay));
				myJournalListPanel.add(sum);
			}
		});
	}


	private void loadListOfPaidJournals() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	// load data for signed admin
	public void adminSigned() {
		addPanel.add(journalNameTextBox);
	    addPanel.add(journalDescriptionTextBox);
	    addPanel.add(journalPriceTextBox);
	    addPanel.add(addListButton);
	    addPanel.addStyleName("addPanel");
	    listFlexTable.setText(0, 3, "Remove");
	    inviteToEditLabel.setText("Insert name / description / price of a journal you would like to add");
	    journalListPanel.add(inviteToEditLabel);
	    journalListPanel.add(addPanel);
	    journalListPanel.add(saveAddedJournalsButton);
	    saveAddedJournalsButton.setEnabled(false);
	    
	    ListHandler listhandler = new ListHandler();
		addListButton.addClickHandler(listhandler);
		journalNameTextBox.addKeyUpHandler(listhandler);
		journalDescriptionTextBox.addKeyUpHandler(listhandler);
		journalPriceTextBox.addKeyUpHandler(listhandler);
		
		AddNewJournalHandler addNewJournalHandler = new AddNewJournalHandler();
		saveAddedJournalsButton.addClickHandler(addNewJournalHandler);
	}


	// the sign in button logic
	class SigninHandler implements ClickHandler, KeyUpHandler {

		public void onClick(ClickEvent event) {
			signinButton.setEnabled(false);
			errorLabel.setText("");
			sendNameAndPwdToServer(false, login.getText(), pwd.getText());
		}

		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				signinButton.setEnabled(false);
				errorLabel.setText("");
				sendNameAndPwdToServer(false, login.getText(), pwd.getText());
			}
		}
	}
	
	// the register button logic
	class LoadRegForm implements ClickHandler {

		public void onClick(ClickEvent event) {
			errorLabel.setText("");
			logPanel.clear();
			login.setText("");
			pwd.setText("");
			logPanel.add(text1Label);
			logPanel.add(login2);
			logPanel.add(text2Label);
			logPanel.add(pwd2);
			logPanel.add(text3Label);
			text3Label.setText("Confirm your password:");
			logPanel.add(pwd3);
			logPanel.add(regButton2);
			logPanel.add(signinButton2);
			logPanel.add(errorLabel);
			
			// a handler to register
			RegFormHandler regFormHandler = new RegFormHandler();
			login2.addKeyUpHandler(regFormHandler);
			pwd2.addKeyUpHandler(regFormHandler);
			pwd3.addKeyUpHandler(regFormHandler);
			regButton2.addClickHandler(regFormHandler);
			
			// a handler to return to the sign in dialogue
			signinButton2.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					errorLabel.setText("");
					login2.setText("");
					pwd2.setText("");
					pwd3.setText("");
					logPanel.clear();
					logPanel.add(text1Label);
					text1Label.setText("Enter your login:");
					logPanel.add(login);
					logPanel.add(text2Label);
					text2Label.setText("Enter your password:");
					logPanel.add(pwd);
					logPanel.add(signinButton);
					logPanel.add(regButton);
					logPanel.add(errorLabel);
				}
			});
		}
	}
	
	// sends register request to server 
	class RegFormHandler implements ClickHandler, KeyUpHandler {

		public void onClick(ClickEvent event) {
			if (pwd2.getText().equals(pwd3.getText())) {
				errorLabel.setText("");
				regButton2.setEnabled(false);
				sendNameAndPwdToServer(true, login2.getText(), pwd2.getText());
			}
			else {
				errorLabel.setText("Please check the password");
			}
		}

		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				if (pwd2.getText().equals(pwd3.getText())) {
					errorLabel.setText("");
					regButton2.setEnabled(false);
					sendNameAndPwdToServer(true, login2.getText(), pwd2.getText());
				}
				else {
					errorLabel.setText("Please check the password");
				}
			}
		}

	}
	
	
	class AddToMyListButton implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	// The add button logic (for admin)
	class ListHandler implements ClickHandler, KeyUpHandler {
		
        public void onClick(ClickEvent event) {
          addList(journalNameTextBox.getText(),journalDescriptionTextBox.getText(),Long.valueOf(journalPriceTextBox.getText()));
        }

        public void onKeyUp(KeyUpEvent event) {
          if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            addList(journalNameTextBox.getText(),journalDescriptionTextBox.getText(),Long.valueOf(journalPriceTextBox.getText()));
          }
        }
        
        // adding new Journal to the loaded list of journals
        private void addList(final String name, final String description, final Long price) {
        	if (name == "" || description == "" || price == 0)
        	      return;
    	    int row = listFlexTable.getRowCount();
    	    final Journal journal = new Journal(name, description, price);
    	    listOfJournals.add(journal);
    	    listFlexTable.setText(row, 0, name);
    	    listFlexTable.setText(row, 1, description);
    	    listFlexTable.setText(row, 2, String.valueOf(price));
    	    journalNameTextBox.setText("");
    	    journalDescriptionTextBox.setText("");
    	    journalPriceTextBox.setText("");
    	    journalNameTextBox.setFocus(true);
    	    saveAddedJournalsButton.setEnabled(true);
    	    rawsAdded++;
    	    
    	    Button removeStockButton = new Button("x");
    	    removeStockButton.addStyleDependentName("remove");
    	    removeStockButton.addClickHandler(new ClickHandler() {
    	      public void onClick(ClickEvent event) {
				int removedIndex = listOfJournals.indexOf(journal);
    	        listOfJournals.remove(removedIndex);
    	        listFlexTable.removeRow(removedIndex + 1);
    	        rawsAdded--;
    	      }
    	    });
    	    listFlexTable.setWidget(row, 3, removeStockButton);
        }   
        
        
	}
	
	// The "Save changes" button logic for admin
	class AddNewJournalHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			saveAddedJournalsButton.setEnabled(false);
			List<Journal> journals = new ArrayList<>();
			for (int i = listFlexTable.getRowCount()-rawsAdded; i < listFlexTable.getRowCount(); i++) {
				journals.add(listOfJournals.get(i-1));
			}
			periodicalService.addNewJournals(journals, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
				}

				@Override
				public void onSuccess(Void result) {
					loadListOfJournals();
				}
			});
		}
	
	}
	
	
}