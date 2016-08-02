package wbs.lotto;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// return- wert einheitlich (uhrzeit) liefern, immer neues Date- objekt erzeugen

// TODO	beautify

// TODO ggf. rückdatierung abfangen

// TODO ggf. enum Teilnahme

public final class LottoDatumUtilGZ {

	public static Date ersterZiehungstag(Date abgabeDatum, boolean isMittwoch,
			boolean isSamstag, int abgabeSchlussMittwoch, int abgabeschlussSamstag) {
		if (!isMittwoch && !isSamstag) {
			throw new IllegalArgumentException("invalid...");
		} else if (isMittwoch && isSamstag) {
			return mittwochUndSamstag(abgabeDatum, abgabeSchlussMittwoch,
					abgabeschlussSamstag);
		} else if (isMittwoch) {
			return nurMittwoch(abgabeDatum, abgabeSchlussMittwoch);
		} else
			return nurSamstag(abgabeDatum, abgabeschlussSamstag);
	}

	private static Date nurMittwoch(Date abgabeDatum, int abgabeSchlussMittwoch) {
		Calendar cal = new GregorianCalendar();
		int stunde;
		cal.setTime(abgabeDatum);
		stunde = cal.get(Calendar.HOUR_OF_DAY);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
				&& stunde < abgabeSchlussMittwoch) {
			return abgabeDatum;
		} else {
			return nachsterMittwoch(abgabeDatum);
		}
	}

	private static Date nurSamstag(Date abgabeDatum, int abgabeschlussSamstag) {
		Calendar cal = new GregorianCalendar();
		int stunde;
		cal.setTime(abgabeDatum);
		stunde = cal.get(Calendar.HOUR_OF_DAY);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				&& stunde < abgabeschlussSamstag) {
			return abgabeDatum;
		} else {
			return nachsterSamstag(abgabeDatum);
		}
	}

	private static Date mittwochUndSamstag(Date abgabeDatum,
			int abgabeschlussMittwoch, int abgabeschlussSamstag) {
		// mi und stunde zu gross, do, fr, sa und stunde klein genug: nächster
		// samstag
		Calendar cal = new GregorianCalendar();
		cal.setTime(abgabeDatum);
		int tag = cal.get(Calendar.DAY_OF_WEEK);
		int stunde = cal.get(Calendar.HOUR_OF_DAY);
		if (tag == Calendar.WEDNESDAY && stunde < abgabeschlussMittwoch) {
			return cal.getTime();
		} else if (tag == Calendar.SATURDAY && stunde < abgabeschlussSamstag) {
			return cal.getTime();
		} else if ((tag == Calendar.WEDNESDAY && stunde >= abgabeschlussMittwoch)
				|| tag == Calendar.THURSDAY || tag == Calendar.FRIDAY
				|| (tag == Calendar.SATURDAY && stunde < abgabeschlussSamstag)) {
			return nachsterSamstag(abgabeDatum);
		} else {
			return nachsterMittwoch(abgabeDatum);
		}
	}

	private static Date nachsterMittwoch(Date abgabeDatum) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(abgabeDatum);
		int calDay = cal.get(Calendar.DAY_OF_WEEK);
		int mittwoch = Calendar.WEDNESDAY;
		int diff = calDay - mittwoch;
		if (diff < 0) {
			cal.add(Calendar.DAY_OF_MONTH, -diff);
		} else {
			cal.add(Calendar.DAY_OF_MONTH, 7 - diff);
		}
		return cal.getTime();
	}

	private static Date nachsterSamstag(Date abgabeDatum) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(abgabeDatum);
		int calDay = cal.get(Calendar.DAY_OF_WEEK);
		int samstag = Calendar.SATURDAY;
		int diff = calDay - samstag;
		if (diff < 0) {
			cal.add(Calendar.DAY_OF_MONTH, -diff);
		} else {
			cal.add(Calendar.DAY_OF_MONTH, 7 - diff);
		}
		return cal.getTime();
	}
}