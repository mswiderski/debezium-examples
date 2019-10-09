package org.acme.vegatables.guard.wih;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.vegatables.guard.TransactionEvent;
import org.acme.vegatables.guard.VegetableEvent;
import org.kie.api.runtime.KieSession;
import org.kie.kogito.rules.KieRuntimeBuilder;

@ApplicationScoped
public class GuardService {

    @Inject
    KieRuntimeBuilder kruntimeBuilder;
    
    private KieSession workinMemory;
    
    @PostConstruct
    public void setup() {
        
        this.workinMemory = kruntimeBuilder.newKieSession();
    }
    
    @PreDestroy
    public void cleanup() {
        this.workinMemory.dispose();
    }
    
    public TransactionEvent processTransaction(TransactionEvent event) {
        if (event == null) {
            throw new RuntimeException("Missing transaction event");
        }
        
        workinMemory.insert(event);
        
        workinMemory.fireAllRules();
        return event;
    }

    public VegetableEvent processVegetable(VegetableEvent event) {
        if (event == null) {
            throw new RuntimeException("Missing transaction event");
        }
        
        workinMemory.insert(event);
        
        workinMemory.fireAllRules();
        return event;
    }
}
