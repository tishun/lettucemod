package com.redis.lettucemod.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.redis.lettucemod.protocol.SearchCommandArgs;
import com.redis.lettucemod.protocol.SearchCommandKeyword;
import com.redis.lettucemod.search.Reducers.Avg;
import com.redis.lettucemod.search.Reducers.Count;
import com.redis.lettucemod.search.Reducers.CountDistinct;
import com.redis.lettucemod.search.Reducers.CountDistinctish;
import com.redis.lettucemod.search.Reducers.FirstValue;
import com.redis.lettucemod.search.Reducers.Max;
import com.redis.lettucemod.search.Reducers.Min;
import com.redis.lettucemod.search.Reducers.Quantile;
import com.redis.lettucemod.search.Reducers.RandomSample;
import com.redis.lettucemod.search.Reducers.StdDev;
import com.redis.lettucemod.search.Reducers.Sum;
import com.redis.lettucemod.search.Reducers.ToList;

import io.lettuce.core.internal.LettuceAssert;

@SuppressWarnings("rawtypes")
public class GroupOperation implements AggregateOperation {

	private final String[] properties;
	private final Reducer[] reducers;

	public GroupOperation(String[] properties, Reducer[] reducers) {
		LettuceAssert.notNull(properties, "Properties must not be null");
		LettuceAssert.noNullElements(properties, "Property elements must not be null");
		LettuceAssert.notEmpty(reducers, "Group must have at least one reducer");
		LettuceAssert.noNullElements(reducers, "Reducer elements must not be null");
		this.properties = properties;
		this.reducers = reducers;
	}

	@Override
	public void build(SearchCommandArgs args) {
		args.add(SearchCommandKeyword.GROUPBY);
		args.add(properties.length);
		for (String property : properties) {
			args.addProperty(property);
		}
		for (Reducer reducer : reducers) {
			reducer.build(args);
		}
	}

	public static GroupByBuilder property(String property) {
		return properties(property);
	}

	public static GroupByBuilder properties(String... properties) {
		return new GroupByBuilder(properties);
	}

	public static GroupByBuilder avg(Avg avg) {
		return reducer(avg);
	}

	public static GroupByBuilder count(Count count) {
		return reducer(count);
	}

	public static GroupByBuilder countDistinct(CountDistinct countDistinct) {
		return reducer(countDistinct);
	}

	public static GroupByBuilder countDistinctish(CountDistinctish countDistinctish) {
		return reducer(countDistinctish);
	}

	public static GroupByBuilder firstValue(FirstValue firstValue) {
		return reducer(firstValue);
	}

	public static GroupByBuilder min(Min min) {
		return reducer(min);
	}

	public static GroupByBuilder max(Max max) {
		return reducer(max);
	}

	public static GroupByBuilder quantile(Quantile quantile) {
		return reducer(quantile);
	}

	public static GroupByBuilder randomSample(RandomSample randomSample) {
		return reducer(randomSample);
	}

	public static GroupByBuilder stdDev(StdDev stdDev) {
		return reducer(stdDev);
	}

	public static GroupByBuilder sum(Sum sum) {
		return reducer(sum);
	}

	public static GroupByBuilder toList(ToList toList) {
		return reducer(toList);
	}

	public static GroupByBuilder reducer(Reducer reducer) {
		return new GroupByBuilder().reducer(reducer);
	}

	public static class GroupByBuilder {

		private final List<String> properties = new ArrayList<>();
		private final List<Reducer> reducers = new ArrayList<>();

		public GroupByBuilder(String... properties) {
			Collections.addAll(this.properties, properties);
		}

		public GroupByBuilder property(String property) {
			return new GroupByBuilder(property);
		}

		public GroupByBuilder avg(Avg avg) {
			return reducer(avg);
		}

		public GroupByBuilder count(Count count) {
			return reducer(count);
		}

		public GroupByBuilder countDistinct(CountDistinct countDistinct) {
			return reducer(countDistinct);
		}

		public GroupByBuilder countDistinctish(CountDistinctish countDistinctish) {
			return reducer(countDistinctish);
		}

		public GroupByBuilder firstValue(FirstValue firstValue) {
			return reducer(firstValue);
		}

		public GroupByBuilder min(Min min) {
			return reducer(min);
		}

		public GroupByBuilder max(Max max) {
			return reducer(max);
		}

		public GroupByBuilder quantile(Quantile quantile) {
			return reducer(quantile);
		}

		public GroupByBuilder randomSample(RandomSample randomSample) {
			return reducer(randomSample);
		}

		public GroupByBuilder stdDev(StdDev stdDev) {
			return reducer(stdDev);
		}

		public GroupByBuilder sum(Sum sum) {
			return reducer(sum);
		}

		public GroupByBuilder toList(ToList toList) {
			return reducer(toList);
		}

		public GroupByBuilder reducer(Reducer reducer) {
			return reducers(reducer);
		}

		public GroupByBuilder reducers(Reducer... reducers) {
			Collections.addAll(this.reducers, reducers);
			return this;
		}

		public GroupOperation build() {
			return new GroupOperation(properties.toArray(new String[0]), reducers.toArray(new Reducer[0]));
		}

	}

}
