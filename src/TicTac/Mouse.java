/*    */ package TicTac;
/*    */ 
/*    */ import java.awt.event.MouseEvent;
/*    */ import java.awt.event.MouseListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Mouse
/*    */   implements MouseListener
/*    */ {
/*    */   Game tictac;
/*    */   
/* 17 */   public Mouse(Game tictac) { this.tictac = tictac; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseClicked(MouseEvent e) {
/* 22 */     if (e.getButton() == 1)
/* 23 */       if (this.tictac.paused == true) {
/* 24 */         this.tictac.restart();
/*    */       }
/*    */       else {
/*    */         
/* 28 */         int x = e.getX();
/* 29 */         int y = e.getY();
/* 30 */         if (x > 80 && x < 560 && y < 480) {
/* 31 */           x -= 80;
/*    */           
/* 33 */           int fx = x / 32;
/* 34 */           int fy = y / 32;
/*    */           
/* 36 */           this.tictac.handler.playerMove(fx, fy);
/*    */         
/*    */         }
/* 39 */         else if (x > 10 && x < 70 && y > 10 && y < 40) {
/* 40 */           this.tictac.handler.undo();
/*    */         }
/* 42 */         else if (x > 10 && x < 70 && y > 50 && y < 80) {
/* 43 */           this.tictac.handler.restart();
/*    */         }
/* 45 */         else if (x > 10 && x < 70 && y > 90 && y < 120) {
/* 46 */           this.tictac.handler.toggleDebug();
/* 47 */           if (this.tictac.handler.visual == true) {
/* 48 */             this.tictac.handler.toggleVisual();
/*    */           }
/*    */         }
/* 51 */         else if (x > 10 && x < 70 && y > 130 && y < 160) {
/* 52 */           this.tictac.handler.toggleVisual();
/*    */         }
/* 54 */         else if (x > 10 && x < 70 && y > 170 && y < 200) {
/* 55 */           this.tictac.handler.worstCase();
/*    */         } 
/*    */       }  
/*    */   }
/*    */   
/*    */   public void mouseEntered(MouseEvent e) {}
/*    */   
/*    */   public void mouseExited(MouseEvent e) {}
/*    */   
/*    */   public void mousePressed(MouseEvent e) {}
/*    */   
/*    */   public void mouseReleased(MouseEvent e) {}
/*    */ }


/* Location:              D:\TicTac.jar!\TicTac\Mouse.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */