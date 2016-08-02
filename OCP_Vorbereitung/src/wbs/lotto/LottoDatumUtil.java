package wbs.lotto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/*  
 * wir schreiben eine methode ersterZiehungstag().
 * sie berechnet das datum, an dem ein eingereichter lottoschein zum 1. mal
 * an einer ziehung teilnimmt.
 * abgabeSchlussMittwoch: 18 (18 Uhr)
 * abgabeSchlussSamstag: 19 (19 Uhr)
 */
public class LottoDatumUtil {
	public static int bisMittwoch(Calendar abgabeDatum) {
		int result = Calendar.WEDNESDAY - abgabeDatum.get(Calendar.DAY_OF_WEEK);
		if (result < 0) {
			result = 7 - (result * -1);
		}
		return result;
	}

	public static int bisSamstag(Calendar abgabeDatum) {
		int result = Calendar.SATURDAY - abgabeDatum.get(Calendar.DAY_OF_WEEK);
		if (result < 0) {
			result = 7 - (result * -1);
		}
		return result;
	}

	public static Date ersterZiehungstag(Date abgabeDatum, boolean isMittwoch, boolean isSamstag,
			int abgabeSchlussMittwoch, int abgabeSchlussSamstag) {
		Date result = new Date();
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.GERMANY);
		cal.setTime(abgabeDatum);

		String abgabetag = sdf.format(abgabeDatum);
		// System.out.println(abgabetag);

		switch (abgabetag) {

		case "Mittwoch":
			if (isMittwoch)
				//will Mittwoch spielen
				if (cal.get(Calendar.HOUR_OF_DAY) >= abgabeSchlussMittwoch) {
					//  ist aber für heute zu spät
					if (isSamstag) {
						// möchte auch am Samstag spielen - also gibt es Samstag
						cal.add(Calendar.DAY_OF_WEEK, bisSamstag(cal));
					} else {
						// möchte nicht am Samstag spielen also Mittwoch nächste
						// Woche
						cal.add(Calendar.DAY_OF_WEEK, 7);
					}

				} else {
					//rechtzeitig für heute
						
				} else
					
					 if (isSamstag) {
					 cal.add(Calendar.DAY_OF_WEEK, bisSamstag(cal));
					 }

				
			break;

		case "Samstag":
			if (isSamstag)
				//will Samstag spielen
				if (cal.get(Calendar.HOUR_OF_DAY) >= abgabeSchlussSamstag) {
					//  ist aber für heute zu spät
					if (isMittwoch) {
						// möchte auch am Samstag spielen - also gibt es Mittwoch
						cal.add(Calendar.DAY_OF_WEEK, bisMittwoch(cal));
					} else {
						// möchte nicht am Mittwoch spielen also Samstag nächste
						// Woche
						cal.add(Calendar.DAY_OF_WEEK, 7);
					}

				} else {
					//rechtzeitig für heute
						
				} else
					
					 if (isMittwoch) {
					 cal.add(Calendar.DAY_OF_WEEK, bisMittwoch(cal));
					 }

				
			break;
		default:
			if (isMittwoch & isSamstag) {
				if (bisMittwoch(cal) < bisSamstag(cal)) {
					cal.add(Calendar.DAY_OF_WEEK, bisMittwoch(cal));
				} else {
					cal.add(Calendar.DAY_OF_WEEK, bisSamstag(cal));
				}
			} else if (isMittwoch) {
				cal.add(Calendar.DAY_OF_WEEK, bisMittwoch(cal));
			} else if (isSamstag) {
				cal.add(Calendar.DAY_OF_WEEK, bisSamstag(cal));
			}
			break;
		}result=cal.getTime();return result;
}

}
