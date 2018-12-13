
package com.fty.baymax.sqlbuilder.condition;


public enum LikeMatch {

	EXACT {
		@Override
		public String toMatchString(String pattern) {
			return pattern;
		}
	},

	START {
		@Override
		public String toMatchString(String pattern) {
			return pattern + '%';
		}
	},

	END {
		@Override
		public String toMatchString(String pattern) {
			return '%' + pattern;
		}
	},

	ANYWHERE {
		@Override
		public String toMatchString(String pattern) {
			return '%' + pattern + '%';
		}
	};

	public abstract String toMatchString(String pattern);

}
