package Laser;

import Filter.Filter_interface;
import org.micromanager.MenuPlugin;
import org.micromanager.Studio;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;

@Plugin(type = MenuPlugin.class)
public class Laser_plugin implements MenuPlugin, SciJavaPlugin {
    private Studio studio;
    Laser_interface LasermainFrame = new Laser_interface();
    @Override
    public String getSubMenu() {
        return "Automation";
    }

    @Override
    public void onPluginSelected() {
        LasermainFrame.setupLaserInterface(studio);
        LasermainFrame.setupLogger();
    }

    @Override
    public void setContext(Studio studio) {
        this.studio = studio;
    }

    @Override
    public String getName() {
        return "Laser Control";
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