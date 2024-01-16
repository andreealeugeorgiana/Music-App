package app.pages.navigation;

import app.pages.Page;
import app.user.User;

import java.util.ArrayList;
import java.util.List;

public class PageNavigation {
    private List<Page> pages;
    private Page firstPage;
    private Page lastPage;
    private User user;

    /**
     * Constructs a PageNavigation object for a specific user.
     *
     * @param user The user for whom the navigation system is created.
     */
    public PageNavigation(final User user) {
        pages = new ArrayList<>();
        firstPage = user.getCurrentPage();
        lastPage = user.getCurrentPage();
        this.user = user;
        pages.add(user.getCurrentPage());
    }

    /**
     * Adds a page to the navigation system, updating the list of pages and the lastPage reference.
     *
     * @param page The page to be added.
     */
    public void addPage(final Page page) {
        pages.remove(page);
        pages.add(page);
        lastPage = page;
    }

    /**
     * Navigates to the next page in the sequence, updating the current page for the user.
     *
     * @return A message indicating the success or failure of the navigation.
     */
    public String nextPage() {
        if (user.getCurrentPage() == lastPage) {
            return "There are no pages left to go forward.";
        } else {
            user.setCurrentPage(pages.get(pages.indexOf(user.getCurrentPage()) + 1));
            return "The user %s has navigated successfully to the next page."
                    .formatted(user.getUsername());
        }
    }

    /**
     * Navigates to the previous page in the sequence, updating the current page for the user.
     *
     * @return A message indicating the success or failure of the navigation.
     */
    public String previousPage() {
        if (user.getCurrentPage().equals(pages.get(0))) {
            return "There are no pages left to go back.";
        } else {
            user.setCurrentPage(pages.get(pages.indexOf(user.getCurrentPage()) - 1));
            return "The user %s has navigated successfully to the previous page."
                    .formatted(user.getUsername());
        }
    }
}
