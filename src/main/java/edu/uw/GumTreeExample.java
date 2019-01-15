package edu.uw;

import java.util.List;
import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.gen.Generators;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;

public class GumTreeExample {

  public static void main(String[] args) throws Exception {
    String buggyFilePath =
        GumTreeExample.class.getClassLoader().getResource("buggy/NumberUtils.java").getFile();
    String fixedFilePath =
        GumTreeExample.class.getClassLoader().getResource("fixed/NumberUtils.java").getFile();

    Run.initGenerators();

    ITree buggyTree = Generators.getInstance().getTree(buggyFilePath).getRoot();
    ITree fixedTree = Generators.getInstance().getTree(fixedFilePath).getRoot();

    Matcher matcher = Matchers.getInstance().getMatcher(buggyTree, fixedTree);
    matcher.match();

    ActionGenerator actionGenerator =
        new ActionGenerator(buggyTree, fixedTree, matcher.getMappings());
    actionGenerator.generate();
    List<Action> actions = actionGenerator.getActions();
    for (Action action : actions) {
      System.out.println(action.toString());
    }

    System.exit(0);
  }
}
