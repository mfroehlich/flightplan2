package com.prodyna.pac.flightplan.client.util;

import static java.util.ResourceBundle.getBundle;

import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;

import com.prodyna.pac.flightplan.common.exception.UserNotLoggedInException;

public abstract class FXMLView {

    public final static String DEFAULT_ENDING = "view";
    protected FXMLLoader fxmlLoader;
    private ResourceBundle bundle;

    public FXMLView() {
        this.init(getClass(), getFXMLName());
    }

    private void init(Class clazz, final String conventionalName) {
        final URL resource = clazz.getResource(conventionalName);
        String bundleName = getBundleName();
        this.bundle = getResourceBundle(bundleName);
        this.fxmlLoader = loadSynchronously(resource, bundle, conventionalName);
    }

    FXMLLoader loadSynchronously(final URL resource, ResourceBundle bundle, final String conventionalName)
            throws IllegalStateException {
        final FXMLLoader loader = new FXMLLoader(resource, bundle);
        try {
            loader.load();
        } catch (LoadException ex) {
            if (ex.getCause() instanceof UserNotLoggedInException) {
                UserNotLoggedInException loggedInEx = (UserNotLoggedInException) ex.getCause();
                throw loggedInEx;
            } else {
                throw new IllegalStateException("Cannot load " + conventionalName, ex);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot load " + conventionalName, ex);
        }
        return loader;
    }

    public Parent getView() {
        Parent parent = getLoader().getRoot();
        addCSSIfAvailable(parent);
        return parent;
    }

    /**
     * Scene Builder creates for each FXML document a root container. This method omits the root container (e.g.
     * AnchorPane) and gives you the access to its first child.
     *
     * @return the first child of the AnchorPane
     */
    public Node getViewWithoutRootContainer() {
        final ObservableList<Node> children = getView().getChildrenUnmodifiable();
        if (children.isEmpty()) {
            return null;
        }
        return children.listIterator().next();
    }

    void addCSSIfAvailable(Parent parent) {
        URL uri = getClass().getResource(getStyleSheetName());
        if (uri == null) {
            return;
        }
        String uriToCss = uri.toExternalForm();
        parent.getStylesheets().add(uriToCss);
    }

    String getStyleSheetName() {
        return getConventionalName(".css");
    }

    public Object getPresenter() {
        return this.getLoader().getController();
    }

    String getConventionalName(String ending) {
        return getConventionalName() + ending;
    }

    String getConventionalName() {
        String clazz = this.getClass().getSimpleName().toLowerCase();
        return stripEnding(clazz);
    }

    String getBundleName() {
        String conventionalName = getConventionalName();
        return this.getClass().getPackage().getName() + "." + conventionalName;
    }

    static String stripEnding(String clazz) {
        if (!clazz.endsWith(DEFAULT_ENDING)) {
            return clazz;
        }
        int viewIndex = clazz.lastIndexOf(DEFAULT_ENDING);
        return clazz.substring(0, viewIndex);
    }

    final String getFXMLName() {
        return getConventionalName(".fxml");
    }

    public static ResourceBundle getResourceBundle(String name) {
        try {
            return getBundle(name);
        } catch (MissingResourceException ex) {
            return null;
        }
    }

    /**
     *
     * @return an existing resource bundle, or null
     */
    public ResourceBundle getResourceBundle() {
        return this.bundle;
    }

    FXMLLoader getLoader() {
        return this.fxmlLoader;
    }
}
