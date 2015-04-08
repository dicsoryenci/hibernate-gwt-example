package cz.cvut.wa2.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import cz.cvut.wa2.client.dto.CarDTO;
import cz.cvut.wa2.client.dto.ManufacturerDTO;
import cz.cvut.wa2.client.dto.UserDTO;

public class wa2 implements EntryPoint {

    /**
     * The standard entry point onModuleLoad() method.
     */
    public void onModuleLoad() {
        final UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);
        final CarServiceAsync carService = (CarServiceAsync) GWT.create(CarService.class);

        final TabPanel mainPanel = new TabPanel();

        // Create and setup the creation panel to add accounts / records.
        final VerticalPanel createEntriesPanel = new VerticalPanel();
        final VerticalPanel addUserPanel = new VerticalPanel();
        final VerticalPanel addCarPanel = new VerticalPanel();

        createEntriesPanel.setSize("500px", "500px");
        createEntriesPanel.setBorderWidth(1);

        constructAddUserPanel(addUserPanel, userService);
        constructAddCarPanel(addCarPanel, carService);

        createEntriesPanel.add(addUserPanel);
        createEntriesPanel.add(addCarPanel);
        mainPanel.add(createEntriesPanel, "Add users/cars");

        final HorizontalPanel borrowPanel = new HorizontalPanel();
        constructBorrowPanel(borrowPanel, userService);
        mainPanel.add(borrowPanel, "Borrow the car");

        final VerticalPanel usersPanel = new VerticalPanel();
        constructUserListingPanel(usersPanel, carService);
        mainPanel.add(usersPanel, "View users");

        mainPanel.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                int selected = event.getSelectedItem();
                switch (selected) {
                    case 0:
                        loadBrands(addCarPanel, carService);
                        break;
                    case 1:
                        loadUsers(borrowPanel, userService);
                        loadCars(borrowPanel, carService);
                        break;
                    case 2:
                        loadUsers(usersPanel, userService);
                        break;
                }

            }
        });

        mainPanel.selectTab(0);
        RootPanel.get().add(mainPanel);
    }

    private void constructUserListingPanel(VerticalPanel viewAccountRecordsPanel, final CarServiceAsync musicStoreService) {
        viewAccountRecordsPanel.setSize("500px", "500px");
        viewAccountRecordsPanel.setBorderWidth(1);

        // Setup the account records table along with headers.
        final FlexTable records = new FlexTable();
        records.insertRow(0);
        records.setText(0, 0, "#");
        records.setText(0, 1, "Name");
        records.setCellSpacing(10);
        records.setCellPadding(5);

        // Add the table and load all accounts and their records into it.
        viewAccountRecordsPanel.add(records);
    }

    private void loadUsers(VerticalPanel usersPanel, final UserServiceAsync userService) {

        final FlexTable records = (FlexTable) usersPanel.getWidget(0);
        userService.getUsers(new AsyncCallback<List<UserDTO>>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get users.");
            }

            @Override
            public void onSuccess(List<UserDTO> result) {
                if (result == null)
                    return;

                for (int i = records.getRowCount() - 1; i > 0; i--) {
                    if (records.isCellPresent(i, 0)) {
                        records.clearCell(i, 0);
                        records.clearCell(i, 1);
                    } else {
                        records.clearCell(i, 1);
                    }
                }

                int index = 1;
                for (UserDTO user : result) {
                    String borrowedCars = "";
                    for (CarDTO c : user.getCars()) {
                        borrowedCars += c.getName() + ",";
                    }
                    records.setText(index, 0, String.valueOf(user.getId()));
                    records.setText(index, 1, user.getName() + " (" + borrowedCars + ")");
                    index++;
                }
            }
        });
    }

    private void constructBorrowPanel(HorizontalPanel panel, final UserServiceAsync userService) {
        panel.setSize("500px", "500px");
        panel.setBorderWidth(1);

        Label userIdLabel = new Label("User Id:");
        final ListBox usersIds = new ListBox();
        Label carId = new Label("Car Id:");
        final ListBox cardIds = new ListBox();
        final Button addRecord = new Button("Add Record");

        // Size up list boxes.
        usersIds.setSize("100px", "35px");
        cardIds.setSize("100px", "35px");

        panel.add(userIdLabel);
        panel.add(usersIds);
        panel.add(carId);
        panel.add(cardIds);

        addRecord.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int userIndex = usersIds.getSelectedIndex();
                int carIndex = cardIds.getSelectedIndex();
                Long userId = new Long(usersIds.getValue(userIndex));
                Long carId = new Long(cardIds.getValue(carIndex));

                // Persist the record to the account.
                userService.saveBorrow(userId, carId,
                        new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Failed to save records.");
                            }

                            @Override
                            public void onSuccess(Void result) {
                                Window.alert("Records saved.");
                            }

                        });
            }
        });
        panel.add(addRecord);
    }

    private void loadBrands(final VerticalPanel panel, final CarServiceAsync carService) {
        carService.getManufacturers(new AsyncCallback<List<ManufacturerDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to retrieve records." + caught.getMessage()); }

            @Override
            public void onSuccess(List<ManufacturerDTO> result) {
                ListBox recordTitles = (ListBox) panel.getWidget(3);
                recordTitles.clear();
                for (ManufacturerDTO record : result) {
                    recordTitles.addItem(record.getTile(), String
                            .valueOf(record.getId()));
                }
            }
        });
    }

    private void loadCars(final HorizontalPanel addRecordsToAccountPanel, final CarServiceAsync carService) {
        carService.getCars(new AsyncCallback<List<CarDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to retrieve records." + caught.getMessage()); }

            @Override
            public void onSuccess(List<CarDTO> result) {
                ListBox recordTitles = (ListBox) addRecordsToAccountPanel.getWidget(3);
                recordTitles.clear();
                for (CarDTO record : result) {
                    recordTitles.addItem(record.getName() + "(" + record.getManufacturer().getTile() + ")", String
                            .valueOf(record.getId()));
                }
            }
        });
    }

    private void loadUsers(final HorizontalPanel panel, final UserServiceAsync userServiceAsync) {
        userServiceAsync.getUsers(new AsyncCallback<List<UserDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to retrieve users." + caught.getMessage());
            }

            @Override
            public void onSuccess(List<UserDTO> result) {
                ListBox selectIds = (ListBox) panel.getWidget(1);
                selectIds.clear();
                for (UserDTO record : result) {
                    selectIds.addItem(String.valueOf(record.getName()), String
                            .valueOf(record.getId()));
                }
            }
        });
    }

    private void constructAddCarPanel(VerticalPanel panel, final CarServiceAsync carsService) {
        panel.setSize("500px", "300px");
        Label carNameLabel = new Label("Car name:");
        final TextBox carName = new TextBox();

        Label manIdLabel = new Label("Manufacturer:");
        final ListBox manIds = new ListBox();

        Button addRecord = new Button("Add");

        addRecord.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int index = manIds.getSelectedIndex();
                Long manId = new Long(manIds.getValue(index));

                CarDTO car = new CarDTO();
                car.setName(carName.getText());
                carsService.saveCar(car, manId, new AsyncCallback<Long>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed to save car.");
                    }

                    @Override
                    public void onSuccess(Long result) {
                        Window.alert("Car saved");
                    }

                });
            }

        });
        panel.add(carNameLabel);
        panel.add(carName);
        panel.add(manIdLabel);
        panel.add(manIds);
        panel.add(addRecord);
    }

    private void constructAddUserPanel(VerticalPanel panel, final UserServiceAsync userService) {
        panel.setSize("500px", "200px");
        Label usernameLabel = new Label("Username:");
        final TextBox username = new TextBox();
        Button addButton = new Button("Add");

        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                UserDTO user = new UserDTO();
                user.setName(username.getText());
                userService.saveUser(user, new AsyncCallback<Long>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("Failed to save user.");
                    }

                    public void onSuccess(Long result) {
                        Window.alert("User saved");
                    }
                });
            }
        });
        panel.add(usernameLabel);
        panel.add(username);
        panel.add(addButton);
    }

}
