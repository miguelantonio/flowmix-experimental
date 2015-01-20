package com.variacode.labs.flowmix;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.calrissian.flowmix.api.aggregator.AbstractAggregator;

/**
 *
 * Awesome Javascript Function Aggregator. This is going to move somewhere over the rainbow
 * 
 * @param <T> Aggregation result type
 * @param <F> Field type
 */
public class JavascriptFunctionAggregator<T,F> extends AbstractAggregator<T,F> {

    private final ScriptEngineManager manager = new ScriptEngineManager();
    private final ScriptEngine engine = manager.getEngineByName("js");
    private final Invocable invocable;
    
    //This is optional, but I think it's simpler for someone trying to focus on the Function implementation
    //by this class not being Abstract
    /*
    * @param javascript has to have an implementation of postAddition(F fieldValue), evict(F fieldValue) and T aggregateResult()
    */
    public JavascriptFunctionAggregator(String outputField, String javascript) throws ScriptException {
        super();
        super.outputField = outputField;
        engine.eval(javascript);
        this.invocable = (Invocable) engine;
    }

    @Override
    protected String getOutputField() {
        return null;
    }

    @Override
    public void add(F fieldValue) {
        try {
            this.invocable.invokeFunction("add", fieldValue );
        } catch (ScriptException | NoSuchMethodException ex) {
            Logger.getLogger(JavascriptFunctionAggregator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void evict(F fieldValue) {
        try {
            this.invocable.invokeFunction("evict", fieldValue);
        } catch (ScriptException | NoSuchMethodException ex) {
            Logger.getLogger(JavascriptFunctionAggregator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected T aggregateResult() {
        try {
            return (T) this.invocable.invokeFunction("aggregateResult", (new Object[0]));
        } catch (ScriptException | NoSuchMethodException ex) {
            Logger.getLogger(JavascriptFunctionAggregator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
