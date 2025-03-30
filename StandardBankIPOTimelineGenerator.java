import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StandardBankIPOTimelineGenerator {

    public static List<IPOPhase> generateTimeline(LocalDate ipoDate) {
        List<IPOPhase> timeline = new ArrayList<>();

        LocalDate pricingDate = ipoDate.minusDays(1);
        LocalDate roadshowStart = pricingDate.minusWeeks(2);
        LocalDate filingDate = roadshowStart.minusMonths(3);
        LocalDate auditStart = filingDate.minusMonths(3);
        LocalDate preparationStart = auditStart.minusMonths(6);

        timeline.add(new IPOPhase("Pre-Preparation Phase", preparationStart, auditStart.minusDays(1)));
        timeline.add(new IPOPhase("Financial Audit", auditStart, filingDate.minusDays(1)));
        timeline.add(new IPOPhase("FSCA & JSE Filling", filingDate, roadshowStart.minusDays(1)));
        timeline.add(new IPOPhase("Investor Roadshow", roadshowStart, pricingDate.minusDays(1)));
        timeline.add(new IPOPhase("Pricing Date", pricingDate, pricingDate));
        timeline.add(new IPOPhase("IPO Launch", ipoDate, ipoDate));

        return timeline;
    }

    public record IPOPhase(String phaseName, LocalDate startDate, LocalDate endDate) {
        @Override
        public String toString() {
            return String.format("%s|%s|%s", phaseName, startDate, endDate);
        }
    }
}
