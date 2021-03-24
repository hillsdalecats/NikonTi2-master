package Main;

import org.micromanager.MenuPlugin;
import org.micromanager.Studio;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;
import ucar.units.Test;

@Plugin(type = MenuPlugin.class)
public class ConfocalMicroscopeAutomationPlugin implements MenuPlugin, SciJavaPlugin {
    private Studio studio;
    MainInterface mainFrame = new MainInterface();
    Test test = new Test();
    @Override
    public String getSubMenu() {
        return "Automation";
    }

    @Override
    public void onPluginSelected() {
        try{
            mainFrame.setupMainInterface(studio);
        } catch (Exception e){
            e.printStackTrace();
        }
        mainFrame.setupLogger();
    }

    @Override
    public void setContext(Studio studio) {
        this.studio = studio;
    }

    @Override
    public String getName() {
        return "Confocal Microscopy Automation";
    }

    @Override
    public String getHelpText() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getCopyright() {
        return null;
    }
}