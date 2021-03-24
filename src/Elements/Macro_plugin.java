package Elements;

import Filter.Filter_interface;
import Laser.Laser_interface;
import org.micromanager.MenuPlugin;
import org.micromanager.Studio;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;

@Plugin(type = MenuPlugin.class)
public class Macro_plugin implements MenuPlugin, SciJavaPlugin {
    private Studio studio;
    Macro_interface MacromainFrame = new Macro_interface();
    @Override
    public String getSubMenu() {
        return "Automation";
    }

    @Override
    public void onPluginSelected() {
        MacromainFrame.setupLaserInterface(studio);
        MacromainFrame.setupLogger();
    }

    @Override
    public void setContext(Studio studio) {
        this.studio = studio;
    }

    @Override
    public String getName() {
        return "Macro Editor";
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