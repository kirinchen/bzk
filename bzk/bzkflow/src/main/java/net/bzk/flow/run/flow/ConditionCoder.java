package net.bzk.flow.run.flow;

import net.bzk.flow.infra.PolyglotEngine;
import net.bzk.infrastructure.PolyglotUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.bzk.flow.model.Condition.ConditionCode;
import net.bzk.infrastructure.ex.BzkRuntimeException;

@Service("net.bzk.flow.model.Condition$ConditionCode")
@Scope("prototype")
public class ConditionCoder extends Conditioner<ConditionCode> {

    public ConditionCoder() {
        super(ConditionCode.class);
    }

    @Override
    public boolean checkSelf() {
        Object o = this.getPolyglotEngine().parseCode(getModel().getPolyglot().toString(), getModel().getCode());
        if (o instanceof Boolean) {
            Boolean b = (Boolean) o;
            return b;
        }
        throw new BzkRuntimeException("not support type:" + o);
    }

}
