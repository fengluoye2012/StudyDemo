package fengluoye.com.plugin;


public class PluginClass {
    public String getResources() {
        return MyApplication.getContext().getResources().getString(R.string.plugin);
    }
}
