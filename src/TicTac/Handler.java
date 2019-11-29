/*     */ package TicTac;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferStrategy;
/*     */ import java.util.Random;
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
/*     */ public class Handler
/*     */   implements Runnable
/*     */ {
/*     */   private Game tictac;
/*     */   private int[][] map;
/*     */   private int[][] mask;
/*     */   private int visAlgX;
/*     */   private int visAlgY;
/*     */   private int func;
/*     */   private long start;
/*     */   private long end;
/*     */   private long delta;
/*  37 */   private int player = 1;
/*  38 */   private int ai = 2;
/*  39 */   private int turn = 1;
/*     */   
/*     */   private Random rand;
/*     */   boolean debug = false;
/*     */   boolean visual = false;
/*  44 */   String msg = "Draw";
/*  45 */   int playerWon = 0;
/*     */ 
/*     */   
/*  48 */   int pAI = 0;
/*  49 */   int pP = 0;
/*     */ 
/*     */   
/*  52 */   int player1X = 0;
/*  53 */   int player1Y = 0;
/*  54 */   int player2X = 0;
/*  55 */   int player2Y = 0;
/*     */   
/*     */   boolean canUndo = false;
/*     */   
/*     */   static final int PLAYER_1 = 1;
/*     */   
/*     */   static final int PLAYER_2 = 2;
/*     */   
/*     */   static final int UP = -1;
/*     */   
/*     */   static final int DOWN = 1;
/*     */   
/*     */   static final int LEFT = -1;
/*     */   
/*     */   static final int RIGHT = 1;
/*     */   
/*     */   static final int CENTER = 0;
/*     */   
/*     */   static final int FINAL = 5;
/*     */   
/*     */   public Handler(Game game) {
/*  76 */     this.rand = new Random();
/*     */     
/*  78 */     this.tictac = game;
/*  79 */     this.map = new int[15][15];
/*  80 */     this.mask = new int[15][15];
/*     */     
/*  82 */     for (int x = 0; x < 15; x++) {
/*  83 */       for (int y = 0; y < 15; y++) {
/*  84 */         this.map[x][y] = 0;
/*  85 */         this.mask[x][y] = 0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  90 */     this.player = this.rand.nextInt(2) + 1;
/*  91 */     switch (this.player) {
/*     */       case 1:
/*  93 */         this.ai = 2;
/*     */         break;
/*     */       case 2:
/*  96 */         this.ai = 1;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restart() {
/* 109 */     this.map = new int[15][15];
/* 110 */     this.mask = new int[15][15];
/*     */     
/* 112 */     for (int x = 0; x < 15; x++) {
/* 113 */       for (int y = 0; y < 15; y++) {
/* 114 */         this.map[x][y] = 0;
/* 115 */         this.mask[x][y] = 0;
/*     */       } 
/*     */     } 
/* 118 */     this.player = this.rand.nextInt(2) + 1;
/* 119 */     switch (this.player) {
/*     */       case 1:
/* 121 */         this.ai = 2;
/*     */         break;
/*     */       case 2:
/* 124 */         this.ai = 1;
/*     */         break;
/*     */     } 
/* 127 */     this.turn = 1;
/*     */   }
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
/*     */   public void playerMove(int fx, int fy) {
/* 142 */     if (this.map[fx][fy] == 0 && this.turn == this.player) {
/* 143 */       this.map[fx][fy] = this.player;
/*     */ 
/*     */       
/* 146 */       this.player1X = fx;
/* 147 */       this.player1Y = fy;
/*     */       
/* 149 */       this.turn = this.ai;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 162 */     if (isWon()) {
/* 163 */       if (this.playerWon == this.player) {
/*     */         
/* 165 */         this.msg = "Player won the game!";
/*     */         
/* 167 */         this.pP++;
/* 168 */       } else if (this.playerWon == this.ai) {
/* 169 */         this.msg = "AI won the game!";
/* 170 */         this.pAI++;
/*     */       } 
/*     */ 
/*     */       
/* 174 */       this.tictac.pause();
/* 175 */     } else if (this.turn == this.ai) {
/* 176 */       logic();
/* 177 */       this.delta = this.end - this.start;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWon() {
/* 188 */     boolean draw = true;
/* 189 */     for (int a = 0; a < 15; a++) {
/* 190 */       for (int b = 0; b < 15; b++) {
/* 191 */         if (this.map[a][b] != 0) {
/*     */           
/* 193 */           int cnt = 0;
/* 194 */           int cond = 5;
/*     */           
/* 196 */           if (checkWin(a, b, -1, 0, cnt, this.map[a][b]) == cond) {
/* 197 */             this.playerWon = this.map[a][b];
/* 198 */             return true;
/*     */           } 
/* 200 */           if (checkWin(a, b, -1, 1, cnt, this.map[a][b]) == cond) {
/* 201 */             this.playerWon = this.map[a][b];
/* 202 */             return true;
/*     */           } 
/* 204 */           if (checkWin(a, b, -1, -1, cnt, this.map[a][b]) == cond) {
/* 205 */             this.playerWon = this.map[a][b];
/* 206 */             return true;
/*     */           } 
/* 208 */           if (checkWin(a, b, 1, 0, cnt, this.map[a][b]) == cond) {
/* 209 */             this.playerWon = this.map[a][b];
/* 210 */             return true;
/*     */           } 
/* 212 */           if (checkWin(a, b, 1, -1, cnt, this.map[a][b]) == cond) {
/* 213 */             this.playerWon = this.map[a][b];
/* 214 */             return true;
/*     */           } 
/* 216 */           if (checkWin(a, b, 1, 1, cnt, this.map[a][b]) == cond) {
/* 217 */             this.playerWon = this.map[a][b];
/* 218 */             return true;
/*     */           } 
/* 220 */           if (checkWin(a, b, 0, -1, cnt, this.map[a][b]) == cond) {
/* 221 */             this.playerWon = this.map[a][b];
/* 222 */             return true;
/*     */           } 
/* 224 */           if (checkWin(a, b, 0, 1, cnt, this.map[a][b]) == cond) {
/* 225 */             this.playerWon = this.map[a][b];
/* 226 */             return true;
/*     */           } 
/*     */         } else {
/* 229 */           draw = false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 233 */     if (draw == true) {
/* 234 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 238 */     return false;
/*     */   }
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
/*     */   public void logic() {
/* 253 */     this.start = System.nanoTime();
/*     */ 
/*     */     
/* 256 */     for (int x = 0; x < 15; x++) {
/* 257 */       for (int y = 0; y < 15; y++) {
/* 258 */         this.mask[x][y] = 0;
/*     */       }
/*     */     } 
/*     */     
/* 262 */     for (int a = 0; a < 15; a++) {
/* 263 */       for (int b = 0; b < 15; b++) {
/*     */         
/* 265 */         if (this.map[a][b] != 0) {
/* 266 */           int cnt = 0;
/* 267 */           evaluate(a, b, 0, -1, cnt, this.map[a][b]);
/* 268 */           evaluate(a, b, 1, -1, cnt, this.map[a][b]);
/* 269 */           evaluate(a, b, 1, 0, cnt, this.map[a][b]);
/* 270 */           evaluate(a, b, 1, 1, cnt, this.map[a][b]);
/* 271 */           evaluate(a, b, 0, 1, cnt, this.map[a][b]);
/* 272 */           evaluate(a, b, -1, 1, cnt, this.map[a][b]);
/* 273 */           evaluate(a, b, -1, 0, cnt, this.map[a][b]);
/* 274 */           evaluate(a, b, -1, -1, cnt, this.map[a][b]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     int max = 0;
/* 283 */     int cnt = 0;
/* 284 */     int x = 0;
/* 285 */     int y = 0;
/* 286 */     boolean win = false;
/*     */     
/* 288 */     for (int a = 0; a < 15; a++) {
/* 289 */       for (int b = 0; b < 15; b++) {
/*     */ 
/*     */         
/* 292 */         if (this.mask[a][b] < 0) {
/* 293 */           win = true;
/* 294 */           x = a;
/* 295 */           y = b;
/*     */         } 
/*     */ 
/*     */         
/* 299 */         if (this.mask[a][b] > max) {
/* 300 */           max = this.mask[a][b];
/* 301 */           cnt = 1;
/*     */         
/*     */         }
/* 304 */         else if (this.mask[a][b] == max) {
/* 305 */           cnt++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     if (win == true) {
/* 311 */       this.map[x][y] = this.ai;
/* 312 */       this.turn = this.player;
/* 313 */       this.end = System.nanoTime();
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 319 */     int c = this.rand.nextInt(cnt);
/* 320 */     for (int a = 0; a < 15; a++) {
/* 321 */       for (int b = 0; b < 15; b++) {
/* 322 */         if (this.mask[a][b] == max && c == 0) {
/* 323 */           x = a;
/* 324 */           y = b;
/* 325 */           c--;
/* 326 */         } else if (this.mask[a][b] == max) {
/* 327 */           c--;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 333 */     if (this.debug == true) {
/* 334 */       System.out.println("field grade is: " + max);
/*     */     }
/*     */ 
/*     */     
/* 338 */     this.map[x][y] = this.ai;
/*     */ 
/*     */     
/* 341 */     this.player2X = x;
/* 342 */     this.player2Y = y;
/* 343 */     this.canUndo = true;
/*     */ 
/*     */     
/* 346 */     this.turn = this.player;
/* 347 */     this.end = System.nanoTime();
/*     */   }
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
/*     */   public void run() {
/* 362 */     this.tictac.createBufferStrategy(3);
/* 363 */     BufferStrategy b = this.tictac.getBufferStrategy();
/*     */     
/* 365 */     while (this.tictac.running) {
/*     */       
/* 367 */       Graphics g = b.getDrawGraphics();
/*     */       
/* 369 */       g.setColor(Color.white);
/* 370 */       g.fillRect(0, 0, 640, 640);
/*     */       
/* 372 */       g.setColor(Color.black);
/* 373 */       for (int x = 0; x < 15; x++) {
/* 374 */         for (int y = 0; y < 15; y++) {
/* 375 */           if (this.map[x][y] == this.player) {
/* 376 */             g.setColor(Color.blue);
/* 377 */             g.setFont(new Font("Serif", 1, 16));
/* 378 */             g.drawString("X", x * 32 + 80 + 8, y * 32 + 22);
/* 379 */           } else if (this.map[x][y] == this.ai) {
/* 380 */             g.setColor(Color.red);
/* 381 */             g.setFont(new Font("Serif", 1, 16));
/* 382 */             g.drawString("O", x * 32 + 80 + 8, y * 32 + 22);
/*     */           } 
/* 384 */           g.setColor(Color.black);
/* 385 */           g.drawRect(x * 32 + 80, y * 32, 32, 32);
/*     */           
/* 387 */           if (this.debug == true) {
/* 388 */             g.setFont(new Font("Serif", 0, 8));
/* 389 */             g.drawString("" + this.mask[x][y], x * 32 + 80 + 2, y * 32 + 8);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 396 */       if (this.visual == true) {
/*     */         
/* 398 */         if (this.func == 1) {
/* 399 */           g.setColor(Color.green);
/* 400 */           g.drawRect(this.visAlgX * 32 + 80 + 2, this.visAlgY * 32 + 2, 28, 28);
/*     */         } 
/* 402 */         if (this.func == 2) {
/* 403 */           g.setColor(Color.yellow);
/* 404 */           g.drawRect(this.visAlgX * 32 + 80 + 2, this.visAlgY * 32 + 2, 28, 28);
/*     */         } 
/*     */       } 
/*     */       
/* 408 */       if (this.tictac.paused == true) {
/* 409 */         g.setFont(new Font("Serif", 1, 16));
/* 410 */         g.drawString(this.msg, 100, 520);
/*     */       } else {
/* 412 */         g.setColor(Color.yellow);
/* 413 */         g.fillRect(10, 10, 60, 30);
/* 414 */         g.setColor(Color.black);
/* 415 */         g.drawRect(10, 10, 60, 30);
/* 416 */         g.setFont(new Font("Serif", 1, 12));
/* 417 */         g.drawString("undo", 20, 30);
/*     */         
/* 419 */         g.setColor(Color.yellow);
/* 420 */         g.fillRect(10, 50, 60, 30);
/* 421 */         g.setColor(Color.black);
/* 422 */         g.drawRect(10, 50, 60, 30);
/* 423 */         g.setFont(new Font("Serif", 1, 12));
/* 424 */         g.drawString("reset", 20, 70);
/*     */         
/* 426 */         g.setColor(this.debug ? Color.green : Color.red);
/* 427 */         g.fillRect(10, 90, 60, 30);
/* 428 */         g.setColor(Color.black);
/* 429 */         g.drawRect(10, 90, 60, 30);
/* 430 */         g.setFont(new Font("Serif", 1, 12));
/* 431 */         g.drawString("debug", 20, 110);
/*     */         
/* 433 */         if (this.debug == true) {
/* 434 */           g.setColor(this.visual ? Color.green : Color.red);
/* 435 */           g.fillRect(10, 130, 60, 30);
/* 436 */           g.setColor(Color.black);
/* 437 */           g.drawRect(10, 130, 60, 30);
/* 438 */           g.setFont(new Font("Serif", 1, 12));
/* 439 */           g.drawString("visual", 20, 150);
/*     */           
/* 441 */           g.setColor(Color.red);
/* 442 */           g.fillRect(10, 170, 60, 30);
/* 443 */           g.setColor(Color.black);
/* 444 */           g.drawRect(10, 170, 60, 30);
/* 445 */           g.setFont(new Font("Serif", 1, 12));
/* 446 */           g.drawString("worst", 20, 190);
/*     */         } 
/*     */       } 
/* 449 */       g.setFont(new Font("Serif", 1, 12));
/* 450 */       g.drawString("Player:AI = " + this.pP + ":" + this.pAI, 500, 500);
/*     */       
/* 452 */       g.setFont(new Font("Serif", 1, 12));
/* 453 */       g.drawString("AI took: " + this.delta + " nanos to make his move!", 80, 500);
/*     */       
/* 455 */       b.show();
/*     */       
/*     */       try {
/* 458 */         Thread.sleep(1L);
/* 459 */       } catch (InterruptedException e) {
/* 460 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */   public void evaluate(int x, int y, int dirX, int dirY, int count, int player) {
/* 481 */     count++;
/*     */ 
/*     */ 
/*     */     
/* 485 */     if (x < 0 || x > 14 || y < 0 || y > 14) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 490 */     if (this.visual == true) {
/* 491 */       this.visAlgX = x;
/* 492 */       this.visAlgY = y;
/* 493 */       this.func = 1;
/*     */       try {
/* 495 */         Thread.sleep(200L);
/* 496 */       } catch (InterruptedException e) {
/* 497 */         e.printStackTrace();
/*     */       } 
/* 499 */       this.func = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 504 */     if (this.mask[x][y] < 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 510 */     if (this.map[x][y] == player) {
/*     */ 
/*     */       
/* 513 */       if (count == 5) {
/*     */         return;
/*     */       }
/*     */       
/* 517 */       evaluate(x + dirX, y + dirY, dirX, dirY, count, player);
/*     */       
/*     */       return;
/*     */     } 
/* 521 */     if (this.map[x][y] != 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 528 */     int res = count;
/* 529 */     res = checkSub(x + dirX, y + dirY, dirX, dirY, count, player);
/*     */ 
/*     */     
/* 532 */     int back = checkSub(x + dirX * -1, y + dirY * -1, dirX * -1, dirY * -1, 0, player) - (count - 1) * 2;
/*     */ 
/*     */     
/* 535 */     if (res < 5) {
/* 536 */       this.mask[x][y] = this.mask[x][y] + count / 2;
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 543 */     res -= 5;
/* 544 */     this.mask[x][y] = this.mask[x][y] + count * 2 + res;
/*     */     
/* 546 */     if (count == 5) {
/* 547 */       updateMask(x, y, player, count);
/*     */       
/* 549 */       if (this.mask[x][y] < 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 555 */       updateMask(x, y, player, count);
/*     */     
/*     */     }
/* 558 */     else if (back != 0 || count + res == 5) {
/* 559 */       updateMask(x, y, player, count + res);
/* 560 */       if (res == 0) {
/* 561 */         updateMask(x, y, player, count + res);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 566 */       updateMask(x, y, player, count + res - 1);
/*     */     } 
/*     */   }
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
/*     */   public int checkWin(int x, int y, int dirX, int dirY, int count, int player) {
/* 584 */     if (x < 0 || x > 14 || y < 0 || y > 14) {
/* 585 */       return count;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 590 */     if (this.map[x][y] == player) {
/* 591 */       count++;
/* 592 */       if (count == 5) {
/* 593 */         return count;
/*     */       }
/* 595 */       return checkWin(x + dirX, y + dirY, dirX, dirY, count, player);
/*     */     } 
/*     */     
/* 598 */     return count;
/*     */   }
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
/*     */   public int checkSub(int x, int y, int dirX, int dirY, int count, int player) {
/* 611 */     int res = 0;
/*     */     
/* 613 */     if (x < 0 || x > 14 || y < 0 || y > 14) {
/* 614 */       return count;
/*     */     }
/*     */     
/* 617 */     if (this.visual == true) {
/* 618 */       this.visAlgX = x;
/* 619 */       this.visAlgY = y;
/* 620 */       this.func = 2;
/*     */       try {
/* 622 */         Thread.sleep(200L);
/* 623 */       } catch (InterruptedException e) {
/* 624 */         e.printStackTrace();
/*     */       } 
/* 626 */       this.func = 0;
/*     */     } 
/*     */     
/* 629 */     if (this.map[x][y] == 0 || this.map[x][y] == player) {
/* 630 */       count++;
/* 631 */       if (this.map[x][y] == player) {
/* 632 */         res++;
/*     */       }
/* 634 */       if (count >= 5) {
/* 635 */         return count + res;
/*     */       }
/* 637 */       res += checkSub(x + dirX, y + dirY, dirX, dirY, count, player);
/* 638 */       return res;
/*     */     } 
/*     */ 
/*     */     
/* 642 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMask(int x, int y, int p, int count) {
/* 653 */     if (this.map[x][y] == 0 && this.ai == p && count == 5) {
/* 654 */       this.mask[x][y] = this.mask[x][y] * -1;
/*     */     }
/* 656 */     if (this.map[x][y] == 0 && this.player == p && count == 5) {
/* 657 */       this.mask[x][y] = this.mask[x][y] * 32;
/*     */     }
/* 659 */     if (this.map[x][y] == 0 && this.ai == p && count == 4) {
/* 660 */       this.mask[x][y] = this.mask[x][y] * 10;
/*     */     }
/* 662 */     if (this.map[x][y] == 0 && this.player == p && count == 4) {
/* 663 */       this.mask[x][y] = this.mask[x][y] * 8;
/*     */     }
/* 665 */     if (this.map[x][y] == 0 && this.ai == p && count == 3) {
/* 666 */       this.mask[x][y] = this.mask[x][y] * 2;
/*     */     }
/* 668 */     if (this.map[x][y] == 0 && this.player == p && count == 3) {
/* 669 */       this.mask[x][y] = this.mask[x][y] * 2;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void undo() {
/* 678 */     if (this.canUndo) {
/* 679 */       this.map[this.player1X][this.player1Y] = 0;
/* 680 */       this.map[this.player2X][this.player2Y] = 0;
/* 681 */       this.canUndo = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleDebug() {
/* 689 */     if (!this.debug) {
/* 690 */       this.debug = true;
/*     */     } else {
/* 692 */       this.debug = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleVisual() {
/* 699 */     if (!this.visual) {
/* 700 */       this.visual = true;
/*     */     } else {
/* 702 */       this.visual = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void worstCase() {
/* 709 */     int flag = 1;
/* 710 */     restart();
/* 711 */     for (int a = 0; a < 14; a++) {
/* 712 */       for (int b = 0; b < 14; b++) {
/* 713 */         if ((a + 1) % 2 == 0 && (b + 1) % 2 == 0) {
/* 714 */           if ((b + 1) % 4 == 0) {
/* 715 */             this.map[a + 1][b] = flag;
/*     */           } else {
/*     */             
/* 718 */             this.map[a][b] = flag;
/*     */           } 
/* 720 */           flag = (flag == 1) ? 2 : 1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\TicTac.jar!\TicTac\Handler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */