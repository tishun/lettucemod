package com.redislabs.mesclun.search;

import com.redislabs.mesclun.search.protocol.CommandKeyword;
import com.redislabs.mesclun.search.protocol.RediSearchCommandArgs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("rawtypes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggetOptions implements RediSearchArgument {

    private boolean fuzzy;
    private boolean withScores;
    private boolean withPayloads;
    private Long max;

    @Override
    public void build(RediSearchCommandArgs args) {
        if (fuzzy) {
            args.add(CommandKeyword.FUZZY);
        }
        if (withScores) {
            args.add(CommandKeyword.WITHSCORES);
        }
        if (withPayloads) {
            args.add(CommandKeyword.WITHPAYLOADS);
        }
        if (max != null) {
            args.add(CommandKeyword.MAX);
            args.add(max);
        }
    }

}