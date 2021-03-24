package Filter;

import org.micromanager.MenuPlugin;
import org.micromanager.Studio;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;

@Plugin(type = MenuPlugin.class)
public class Filter_plugin implements MenuPlugin, SciJavaPlugin {
    private Studio studio;
    Filter_interface FiltermainFrame = new Filter_interface();
    @Override
    public String getSubMenu() {
        return "Automation";
    }

    @Override
    public void onPluginSelected() {
        FiltermainFrame.setupFilterInterface(studio);
        FiltermainFrame.setupLogger();
    }

    @Override
    public void setContext(Studio studio) {
        this.studio = studio;
    }

    @Override
    public String getName() {
        return "Filter Control";
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