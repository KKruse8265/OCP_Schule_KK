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

		Calendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.GERMANY); //format festlegen f�r den Abgabetag
		cal.setTime(abgabeDatum);	//Abgabedatum zur Arbeitsvariablen zuweisen

		String abgabetag = sdf.format(abgabeDatum); //Wochentag des Abgabedatums ermitteln

		switch (abgabetag) {	//Auswerten de Wochentags

		case "Mittwoch":
			if (isMittwoch)
				// will Mittwoch spielen
				if (cal.get(Calendar.HOUR_OF_DAY) >= abgabeSchlussMittwoch) {
				// ist aber f�r heute zu sp�t
				if (isSamstag) {
				// m�chte auch am Samstag spielen - also gibt es Samstag
				cal.add(Calendar.DAY_OF_WEEK, bisSamstag(cal));
				} else {
				// m�chte nicht am Samstag spielen also Mittwoch n�chste
				// Woche
				cal.add(Calendar.DAY_OF_WEEK, 7);
				}

				} else {
				// rechtzeitig f�r heute
				// Datum �ndert sich nicht
				}
			else if (isSamstag) {
				cal.add(Calendar.DAY_OF_WEEK, bisSamstag(cal));
			}
			break;

		case "Samstag":
			if (isSamstag)
				// will Samstag spielen
				if (cal.get(Calendar.HOUR_OF_DAY) >= abgabeSchlussSamstag) {
				// ist aber f�r heute zu sp�t
				if (isMittwoch) {
				// m�chte auch am Samstag spielen - also gibt es Mittwoch
				cal.add(Calendar.DAY_OF_WEEK, bisMittwoch(cal));
				} else {
				// m�chte nicht am Mittwoch spielen also Samstag n�chste
				// Woche
				cal.add(Calendar.DAY_OF_WEEK, 7);
				}
				} else {
				// rechtzeitig f�r heute
				// Datum �ndert sich nicht
				}
			else if (isMittwoch) {
				cal.add(Calendar.DAY_OF_WEEK, bisMittwoch(cal));
			}
			break;
		default://alle anderen Tage
			if (isMittwoch & isSamstag) {
				// beide Tage werden gespielt
				if (bisMittwoch(cal) < bisSamstag(cal)) {
					// Mittwoch liegt dichter am Abgebetag als samstag 
					cal.add(Calendar.DAY_OF_WEEK, bisMittwoch(cal));
				} else {
					// Samstag liegt dichter am Abgabetag als mittwoch
					cal.add(Calendar.DAY_OF_WEEK, bisSamstag(cal));
				}
			} else if (isMittwoch) {
				// nur am mittwoch wird gespielt
				cal.add(Calendar.DAY_OF_WEEK, bisMittwoch(cal));
			} else if (isSamstag) {
				// nur am samstag wird gespielt
				cal.add(Calendar.DAY_OF_WEEK, bisSamstag(cal));
			}
			break;
		}
		return cal.getTime();
	}

}
