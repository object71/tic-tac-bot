/*     */ package TicTac;
/*     */ 
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Dimension;
/*     */ import javax.swing.JFrame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Game
/*     */   extends Canvas
/*     */   implements Runnable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   Handler handler;
/*     */   Thread game;
/*     */   Thread animator;
/*     */   Mouse mouse;
/*     */   boolean running = true;
/*     */   boolean paused = false;
/*     */   
/*     */   public static void main(String[] args) {
/*  53 */     Game tictac = new Game();
/*  54 */     tictac.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Game() {
/*  65 */     JFrame myFrame = new JFrame("Tic-Tac-Toe");
/*  66 */     Mouse mouse = new Mouse(this);
/*  67 */     this.handler = new Handler(this);
/*  68 */     Dimension d = new Dimension(640, 560);
/*     */ 
/*     */     
/*  71 */     myFrame.setDefaultCloseOperation(3);
/*  72 */     myFrame.setMaximumSize(d);
/*  73 */     myFrame.setMinimumSize(d);
/*  74 */     myFrame.setPreferredSize(d);
/*  75 */     myFrame.setResizable(false);
/*     */     
/*  77 */     myFrame.add(this);
/*  78 */     addMouseListener(mouse);
/*     */     
/*  80 */     myFrame.setVisible(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void start() {
/*  90 */     this.game = new Thread(this);
/*  91 */     this.animator = new Thread(this.handler);
/*  92 */     this.game.start();
/*  93 */     this.animator.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 102 */     while (this.running) {
/* 103 */       if (!this.paused)
/* 104 */         this.handler.update(); 
/*     */       try {
/* 106 */         Thread.sleep(1L);
/* 107 */       } catch (InterruptedException e) {
/* 108 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restart() {
/* 119 */     this.paused = false;
/* 120 */     this.handler.restart();
/* 121 */     System.gc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   public void pause() { this.paused = true; }
/*     */ }


/* Location:              D:\TicTac.jar!\TicTac\Game.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */