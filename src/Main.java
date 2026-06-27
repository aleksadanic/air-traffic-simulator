import api.API;
import gui.MainFrame;
import model.data.Scenario;

public class Main {
    public static void main(String[] args) {
        Scenario scenario = new Scenario();
        API api = new API();
        api.setCurrentScenario(scenario);
        api.setMainFrame(new MainFrame(api));
    }
}