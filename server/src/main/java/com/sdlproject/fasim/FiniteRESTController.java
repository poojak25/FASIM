package com.sdlproject.fasim;

import com.sdlproject.FiniteAutometaSimulation.bodies.EndClass;
import com.sdlproject.FiniteAutometaSimulation.bodies.EntryClass;
import com.sdlproject.FiniteAutometaSimulation.machinewithoutoutput.DFA;
import com.sdlproject.FiniteAutometaSimulation.machinewithoutoutput.FiniteAutomata;
import com.sdlproject.FiniteAutometaSimulation.machinewithoutoutput.NFA;
import com.sdlproject.FiniteAutometaSimulation.machinewithoutoutput.TypeOfFA;
import com.sdlproject.FiniteAutometaSimulation.supportclasses.Alphabet;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class FiniteRESTController {
    @PostMapping(value = "/diagram", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EndClass getStates(@RequestBody EntryClass body) {
        EndClass responseBody = new EndClass();

        String nameOfMachine = body.getMachine_name(); // <- String Machine_Name
        TypeOfFA type = body.getMachine_type(); // <- Type of FA
        Alphabet alphabet = new Alphabet(body.getAlphabet_count(), body.getAlphabets()); // <- Alphabets are created
        FiniteAutomata myMachine = type.equals(TypeOfFA.DFA) ? new DFA(alphabet, nameOfMachine) : new NFA(alphabet, nameOfMachine); // <- FA is Created
        myMachine.createStates(body.getState_count(), body.getStates()); // <- Create the States

        responseBody = myMachine.descriptMachine(responseBody);
        responseBody.setAlphabets(body.getAlphabets());
        responseBody.setTest_strings(body.getTest_strings());
        responseBody = myMachine.setTestCases(body.getTest_strings(), responseBody);

        return responseBody;
    }
}
