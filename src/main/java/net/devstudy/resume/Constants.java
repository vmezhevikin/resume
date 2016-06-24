package net.devstudy.resume;

public final class Constants {
	
	public static final int MAX_PROFILES_PER_PAGE = 10;

	public static final String USER = "USER";

	public static final String[] MONTH_NAMES = { "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };

	public static enum ImageType {
		
		AVATAR(400, 400, 100, 100), CERTIFICATE(800, 800, 100, 100);

		private final int largeWidth;
		private final int largeHeight;
		private final int smallWidth;
		private final int smallHeight;

		private ImageType(int largeWidth, int largeHeight, int smallWidth, int smallHeight) {
			this.largeWidth = largeWidth;
			this.largeHeight = largeHeight;
			this.smallWidth = smallWidth;
			this.smallHeight = smallHeight;
		}

		public int getLargeWidth() {
			return largeWidth;
		}

		public int getLargeHeight() {
			return largeHeight;
		}

		public int getSmallWidth() {
			return smallWidth;
		}

		public int getSmallHeight() {
			return smallHeight;
		}
	}
}