package com.ibm.wala.viz.viewer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;

/**
 * Viewer for ClassHeirarcy, CallGraph and Pointer Analysis results.
 * A driver for example can be found in com.ibm.wala.js.rhino.vis.JsViewer.
 * @author yinnonh
 *
 */
public class WalaViewer extends JFrame {

  protected static final String DefaultMutableTreeNode = null;


  public WalaViewer(CallGraph cg, PointerAnalysis pa) {
    setNativeLookAndFeel();
    
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.add("Call Graph", new CgPanel(cg));
    tabbedPane.add("Class Hierarchy", new ChaPanel(cg.getClassHierarchy()));
    PaPanel paPanel = createPaPanel(cg, pa);
    paPanel.init();
    tabbedPane.add("Pointer Analysis", paPanel);

    setSize(600, 800);
    setExtendedState(MAXIMIZED_BOTH);
    addWindowListener(new ExitListener());

    this.setTitle("Wala viewer");
    
    add(tabbedPane);
    setVisible(true);
  }

  protected PaPanel createPaPanel(CallGraph cg, PointerAnalysis pa) {
    return new PaPanel(cg, pa);
  }
  
  public static void setNativeLookAndFeel() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  private static class ExitListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent event) {
      System.exit(0);
    }
  }
}