/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.core.command.runtime;

import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.impl.KnowledgeCommandContext;
import org.kie.internal.command.Context;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.WorkingMemoryEventListener;
import org.kie.runtime.KieSession;

public class AddEventListenerCommand
    implements
    GenericCommand<Object> {

    private WorkingMemoryEventListener workingMemoryEventlistener = null;
    private AgendaEventListener        agendaEventlistener        = null;
    private ProcessEventListener       processEventListener       = null;

    public AddEventListenerCommand(WorkingMemoryEventListener listener) {
        this.workingMemoryEventlistener = listener;
    }

    public AddEventListenerCommand(AgendaEventListener listener) {
        this.agendaEventlistener = listener;
    }
    
    public AddEventListenerCommand(ProcessEventListener listener) {
        this.processEventListener = listener;
    }


    public Void execute(Context context) {
        KieSession ksession = ((KnowledgeCommandContext) context).getKieSession();

        if ( workingMemoryEventlistener != null ) {
            ksession.addEventListener( workingMemoryEventlistener );
        } else if ( agendaEventlistener != null ) {
            ksession.addEventListener( agendaEventlistener );
        } else {
            ksession.addEventListener( processEventListener );
        }
        return null;
    }

    public String toString() {
        if ( workingMemoryEventlistener != null ) {
            return "session.addEventListener( " + workingMemoryEventlistener + " );";
        } else if ( agendaEventlistener != null ) {
            return "session.addEventListener( " + agendaEventlistener + " );";
        }  else  if ( processEventListener != null ) {
            return "session.addEventListener( " + processEventListener + " );";
        }
        
        return "AddEventListenerCommand";
    }
}