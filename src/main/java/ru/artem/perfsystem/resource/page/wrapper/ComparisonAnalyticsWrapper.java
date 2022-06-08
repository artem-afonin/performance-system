package ru.artem.perfsystem.resource.page.wrapper;

import lombok.Getter;
import lombok.Setter;
import ru.artem.perfsystem.util.analytics.wrapper.ComparisonResultWrapper;

public class ComparisonAnalyticsWrapper {

    @Getter
    @Setter
    private ComparisonResultWrapper result;
    @Getter
    private boolean isError = false;
    @Getter
    private String errorMsg = null;

    public void setError(String errorMsg) {
        this.isError = true;
        this.errorMsg = errorMsg;
    }

}
