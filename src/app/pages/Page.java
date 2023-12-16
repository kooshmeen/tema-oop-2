package app.pages;

import app.user.User;

public abstract class Page {
    protected User user;

    public Page(final User user) {
        this.user = user;
    }
    /**
     * @return the page's content
     */

    public abstract String displayContent();
}
