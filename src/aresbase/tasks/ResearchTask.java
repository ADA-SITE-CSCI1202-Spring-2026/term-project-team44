package aresbase.tasks;
import aresbase.model.Resource;

import java.util.*;

 // this is the Parent Task class and other xTask classes extend this class
public class ResearchTask  extends ColonyTask{

    public ResearchTask(String name, int reward, Map<Resource, Integer> required) {

        super(name, reward, required);
    }


     @Override
     public String getType(){
         return "Research";
     }




}
