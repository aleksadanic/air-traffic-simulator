package gui;

import api.API;

import java.awt.*;

public class AppMenuBar extends MenuBar {
    private Frame owner;
    private API api;

    public AppMenuBar(Frame owner, API api) {
        this.owner = owner;
        this.api = api;

        Menu fileMenu = new Menu("File");

        Menu loadMenu = new Menu("Load");
        MenuItem loadCsvItem = new MenuItem("CSV");
        MenuItem loadJsonItem = new MenuItem("JSON");

        loadMenu.add(loadCsvItem);
        loadMenu.add(loadJsonItem);

        Menu saveMenu = new Menu("Save");
        MenuItem saveCsvItem = new MenuItem("CSV");
        MenuItem saveJsonItem = new MenuItem("JSON");

        saveMenu.add(saveCsvItem);
        saveMenu.add(saveJsonItem);

        fileMenu.add(loadMenu);
        fileMenu.add(saveMenu);

        add(fileMenu);

        loadCsvItem.addActionListener(e -> loadCsvScenario());
        loadJsonItem.addActionListener(e -> loadJsonScenario());
        saveCsvItem.addActionListener(e -> saveCsvScenario());
        saveJsonItem.addActionListener(e -> saveJsonScenario());
    }

    private void loadCsvScenario() {
        FileDialog fileDialog = new FileDialog(owner, "Load CSV scenario", FileDialog.LOAD);
        fileDialog.setVisible(true);

        if (fileDialog.getFile() == null) {
            return;
        }

        String path = fileDialog.getDirectory() + fileDialog.getFile();

        try {
            api.loadCsvScenario(path);
            ((MainFrame) owner).refreshAll();
        } catch (Exception e) {
            ErrorDialog.showError(owner, e.getMessage());
        }
    }

    private void loadJsonScenario() {
        FileDialog fileDialog = new FileDialog(owner, "Load JSON scenario", FileDialog.LOAD);
        fileDialog.setVisible(true);

        if (fileDialog.getFile() == null) {
            return;
        }

        String path = fileDialog.getDirectory() + fileDialog.getFile();

        try {
            api.loadJsonScenario(path);
            ((MainFrame) owner).refreshAll();
        } catch (Exception e) {
            ErrorDialog.showError(owner, e.getMessage());
        }
    }

    private void saveCsvScenario() {
        FileDialog fileDialog = new FileDialog(owner, "Save CSV scenario", FileDialog.SAVE);
        fileDialog.setVisible(true);

        if (fileDialog.getFile() == null) {
            return;
        }

        String path = fileDialog.getDirectory() + fileDialog.getFile();

        try {
            api.saveCsvScenario(path);
        } catch (Exception e) {
            ErrorDialog.showError(owner, e.getMessage());
        }
    }

    private void saveJsonScenario() {
        FileDialog fileDialog = new FileDialog(owner, "Save JSON scenario", FileDialog.SAVE);
        fileDialog.setVisible(true);

        if (fileDialog.getFile() == null) {
            return;
        }

        String path = fileDialog.getDirectory() + fileDialog.getFile();

        try {
            api.saveJsonScenario(path);
        } catch (Exception e) {
            ErrorDialog.showError(owner, e.getMessage());
        }
    }
}
