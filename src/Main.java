import api.API;
import gui.MainFrame;
import model.Scenario;

public class Main {
    public static void main(String[] args) {
        Scenario scenario = new Scenario();
        API api = new API();
        api.setCurrentScenario(scenario);
        new MainFrame(api);
    }
}