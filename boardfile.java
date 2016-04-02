import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;
import java.util.Collection;
import java.lang.Object;
import java.util.Arrays;
import java.lang.Math;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.io.FileReader;
import java.util.Random;

class boardfile
{
	static int[][] gameboard = new int[13][13];
	static int[][] mypos = new int[121][121];
	static int[][] priorityres = new int[10][5];
	static int[] nodeone = new int[4];
	static int[] nodetwo = new int[4];
    static int pnode=0;
    static boolean printed=false;
	public static void main(String[] args) throws IOException 
	{
		BufferedReader br=new BufferedReader(new FileReader("board_file.txt"));
		StringTokenizer st=new StringTokenizer(br.readLine());
        int player2,player = Integer.parseInt(st.nextToken());
        if (player==1) {
        	player2=0;
        }
        else
        {
        	player2=1;
        }
		String line;
		int x=0,y=0,row=0;
		while ((line = br.readLine()) != null)
		{
			int time=0;
			for (String l: line.split(" "))
			{
				char c = l.charAt(0);
				if (time==1)
				{
					switch (c)
					{
						case 'U' : gameboard[x][y]=-1;
						           break;
						case 'R' : gameboard[x][y]=1;
									//mypos[row][0]=x;
									//mypos[row][1]=y;
									//row++;
						           break;
						case 'B'  : gameboard[x][y]=0;
						            break;
					}
				}
				else
				{
					 x = (int)(c - 'A') + 1;
					 String l1 = l+"*";
					if (l1.charAt(2)=='0'||l1.charAt(2)=='1') 
						y= ((int)(l.charAt(1)-'1')+1)*10+(int)(l.charAt(2)-'1')+1;
					else
						y= (int)(l.charAt(1)-'1')+1;
					time=1;
				}
			}
		}
		
        
        {
        	int colm=6, roww=6;
        	if(player==1)
		    {
		   	   //System.out.print("A1");
			   if(gameboard[6][6]==-1)
           			{
			        	System.out.print("F6");
			        	printed=true;
			        }
				else
				{
					
					for(colm = 1;colm<12;colm++)
					{
						for(roww=1;roww<12;roww++)
						{
							if( gameboard[roww][colm]==1)
								break;
						}

						if(roww<12)
							break;
					}
					int temprow=roww;
					if (colm!=1)
					{
					for(roww=1;roww<12;roww++)
						{
							if((gameboard[roww+1][colm-1]==-1 || gameboard[roww][colm-1]==-1) && gameboard[roww][colm]==1)
							{
								temprow=roww;
								break;
							}
						}
					}
					nodeone[0]=temprow;
					nodeone[1]=colm;
					//System.out.println("**nodeone = "+nodeone[0]+" "+nodeone[1]);

					for(colm = 11;colm>0;colm--)
					{
						for(roww=1;roww<12;roww++)
						{
							if(gameboard[roww][colm]==1)
								break;
						}
						if(roww<12)
							break;
					}
					temprow=roww;
					//System.out.println("temprow"+temprow);
					if (colm!=11)
					{	
					for(roww=1;roww<12;roww++)
						{
							if((gameboard[roww-1][colm+1]==-1 || gameboard[roww][colm+1]==-1) && gameboard[roww][colm]==1)
							{
								temprow=roww;
								break;
							}
						}
					}
					nodetwo[0]=temprow;
					nodetwo[1]=colm;
					//System.out.println("**nodeone = "+nodetwo[0]+" "+nodetwo[1]);
	
					boolean canbridgeup=true, canbridgedown=true;
					int[][] mat=new int[10][5];
					mat=prioritymatric(player,nodetwo[0],nodetwo[1]);
					//System.out.println("*prioritymatric returned*"+mat[0][0]+" "+mat[0][1]+mat[0][2]+"**"+mat[0][3]);	
					//System.out.println("pnode = "+mat[0][3]);
					
					if (mat[0][4]!=0 && !printed)
					{
					   for (int i=0;i<=mat[0][4] ; i++)
					   {
					   	    //System.out.println("*for loop i= *"+i+" "+mat[i][0]+" "+mat[i][1]+" "+mat[i][2]+" "+mat[i][3]+"**");	
						    if (mat[i][0]==1 && (gameboard[mat[i][1]][mat[i][2]-1]!=player && gameboard[mat[i][1]+1][mat[i][2]-1]!=player))
					     	{
							    canbridgeup=false;
							    canbridgedown=false;
							 //   System.out.println("its here in modification");
 							    if (gameboard[mat[i][1]][mat[i][2]-1]==-1)
 							    {
 								     //gameboard[mat[i][1]][mat[i][2]-1]=player;
 								     System.out.print(inttochar(mat[i][1]));
				                     System.out.print(mat[i][2]-1);
				                     printed=true;
 							    }
 						     	else
 							    {
 							        //gameboard[mat[i][1]+1][mat[i][2]-1]=player;	
 							        System.out.print(inttochar(mat[i][1]+1));
				                     System.out.print(mat[i][2]-1);
				                     printed=true;
 						    	}
					    	}
				   	    }
					
						
					}
					if(nodeone[1]<=2)
						canbridgeup = false;
					//System.out.println("**********canbridgeup==="+canbridgeup);
					if(nodetwo[1]>9)
						canbridgedown = false;
					//System.out.println("***********canbridgedown==="+canbridgedown);
					int[] resultbridge = new int[4];
					/*System.out.println(nodeone[0]);
					System.out.print(" "+nodeone[1]);
					System.out.print(" "+nodetwo[0]);
					System.out.print(" "+nodetwo[1]);	*/
					boolean m1,m2,m3,m4,m5,m6;
					m1=validatemove(nodeone[0],nodeone[1]-1);
                    m2=validatemove(nodeone[0]+1,nodeone[1]-1);
                    m3=validatemove(nodetwo[0],nodetwo[1]+1);
                    m4=validatemove(nodetwo[0]-1,nodetwo[1]+1);
                    int mus=verifypath(player,nodeone[0],nodeone[1],1);
                    int mus2=verifypath(player,nodetwo[0],nodetwo[1],2);
                    //System.out.println("mus and mus2 = "+mus+mus2);

                    if(!printed)
                    {
                    if (mus!=-1 && (m1 || m2))
                    {
                    	/*int mus2,mus3;
                    	mus2=verifybridge2();
                    	mus3=verifybridge2();
                    	*/
                        
                        	if(m1)
							{


								System.out.print(inttochar(nodeone[0]));
				           		System.out.print(nodeone[1]-1);
				           		printed=true;
							}
					    	else if(m2)
							{
								System.out.print(inttochar(nodeone[0]+1));
				            	System.out.print(nodeone[1]-1);
				            	printed=true;
							}
					}
					else if (mus2!=-1 && (m3 || m4))
					{
					        if(m3)
							{
								System.out.print(inttochar(nodetwo[0]));
				            	System.out.print(nodetwo[1]+1);
				            	printed=true;
							}
							else if(m4)
							{
								System.out.print(inttochar(nodetwo[0]-1));
				            	System.out.print(nodetwo[1]+1);
				            	printed=true;
							}
					}	

                    else if(m1 && gameboard[nodeone[0]+1][nodeone[1]-1]==player2)
						{
					//		System.out.println("here1");
							System.out.print(inttochar(nodeone[0]));
				            System.out.print(nodeone[1]-1);
				            printed=true;
						}
					else if(m2 && gameboard[nodeone[0]][nodeone[1]-1]==player2)
						{
					//		System.out.println("here2");
							System.out.print(inttochar(nodeone[0]+1));
				            System.out.print(nodeone[1]-1);
				            printed=true;
						}
					
					else if(m3 && gameboard[nodetwo[0]-1][nodetwo[1]+1]==player2)
						{
					//		System.out.println("here3");
							System.out.print(inttochar(nodetwo[0]));
				            System.out.print(nodetwo[1]+1);
				            printed=true;
						}
					else if(m4 && gameboard[nodetwo[0]][nodetwo[1]+1]==player2)
						{
					//	    System.out.println("here4");
							System.out.print(inttochar(nodetwo[0]-1));
				            System.out.print(nodetwo[1]+1);
				            printed=true;
						}
				    else if (!m1 && !m2 && nodeone[1]!=1)
				    {
				    //	System.out.println("possibility 1");
				    	int x2=nodeone[0];
				    	int y2=nodeone[1];
				    	int[] changeco=new int[2];

                        if (gameboard[x2-1][y2+1]==player)
                        {
                            changeco=newbridge(player,x2-1,y2+1);
                            if (validatemove(changeco[0],changeco[1])) {
                            System.out.print(inttochar(changeco[0]));
				            System.out.print(changeco[1]);
				            printed=true;                            	
                            }
                            else{
                            	printed=false;
                            }


                        }
                        else if (gameboard[x2][y2+1]==player)
                        {
                        	changeco=newbridge(player,x2,y2+1);
                        	
				            if (validatemove(changeco[0],changeco[1])) {
                            System.out.print(inttochar(changeco[0]));
				            System.out.print(changeco[1]);
				            printed=true;                            	
                            }
                            else{
                            	printed=false;
                            }
                        }
				        
				    }
				    else if (!m3 && !m4 && nodetwo[1]!=11)
				    {
				    //	System.out.println("possibility 2");
				    	int x2=nodetwo[0];
				    	int y2=nodetwo[1];
				    	int[] changeco=new int[2];
						if (gameboard[x2+1][y2-1]==player)
                        {
                            changeco=newbridge(player,x2+1,y2-1);
                            System.out.print(inttochar(changeco[0]));
				            System.out.print(changeco[1]);
				            printed=true;
                        }
                        else if (gameboard[x2][y2-1]==player)
                        {
                        	changeco=newbridge(player,x2,y2-1);
                        	System.out.print(inttochar(changeco[0]));
				            System.out.print(changeco[1]);
				            printed=true;
                        }
                    }
					else if((m1 && m2) && canbridgeup)
					{					
						{

							resultbridge = bridge(1,0,nodeone[0],nodeone[1]);
							System.out.print(inttochar(resultbridge[0]));
				            System.out.print(resultbridge[1]);
				            printed=true;
						}
					}
					else if((m3 && m4) && canbridgedown)
					{
					//	System.out.println("it is here baby");
						{
							resultbridge = bridge(1,1,nodetwo[0],nodetwo[1]);
							System.out.print(inttochar(resultbridge[0]));
				            System.out.print(resultbridge[1]);
				            printed=true;
						}
					}
					else if(m1 && m2)
						{
						//	System.out.println("here5");
							System.out.print(inttochar(nodeone[0]));
				            System.out.print(nodeone[1]-1);
				            printed=true;
						}
					/*else if(m2)
						{
							System.out.print(inttochar(nodeone[0]+1));
				            System.out.print(nodeone[1]-1);
						}*/
					
					else if(m3 && m4)
						{
						//	System.out.println("here6");
							System.out.print(inttochar(nodetwo[0]));
				            System.out.print(nodetwo[1]+1);
				            printed=true;
						}
					/*else if(m4 )
						{
							System.out.print(inttochar(nodetwo[0]-1));
				            System.out.print(nodetwo[1]+1);
						}*/
					else if (!printed) 
					{
						//System.out.println("here7");
						int q=mat[0][4];
					//	System.out.println("q= "+q);
						if(q>0)
						{
                             while(mat[q-1][0]!=0)
                             {
                             	q--;
                             }
                             if (mat[q-1][3]==0) {
                                int x1=mat[q-1][1];
                                int y1=mat[q-1][2]-1;
                                System.out.print(inttochar(x1));
	     			             System.out.print(y1);
	     			             printed=true;

                             }
                             else if (mat[q-1][3]==1) {
                             	 int x1=mat[q-1][1]+1;
                                int y1=mat[q-1][2]-1;
                                System.out.print(inttochar(x1));
	     			             System.out.print(y1);
	     			             printed=true;
                             	
                             }
                             else if (mat[q-1][3]==2) {
                             	 int x1=mat[q-1][1];
                                int y1=mat[q-1][2]-1;
                                System.out.print(inttochar(x1));
	     			             System.out.print(y1);
	     			             printed=true;
                             	
                             }

                        
						}
					}
					if (!printed)
					{
			         //   System.out.println("in the randon fn");
	
						int[] randomresu = new int[3];
						randomresu = randommove();
						System.out.print(inttochar(randomresu[0]));
						System.out.print(randomresu[1]);
						printed=true;
    			    }    
				    
				}	
				}
			}
			else
			{
				if(gameboard[6][6]==-1)
           			{
			        	System.out.print("F6");
			        	printed=true;
			        }
			    else if(gameboard[5][9]==-1 && gameboard[6][6]!=0)
			    {
			    	System.out.print("E9");
			    	printed = true;
			    }
				else
				{
					
					for(roww = 1;roww<12;roww++)
					{
						for(colm=1;colm<12;colm++)
						{
							if( gameboard[roww][colm]==0)
								break;
						}

						if(colm<12)
							break;
					}
					int tempcol=colm;
					if (roww!=1)
					{
					    for(colm=1;colm<12;colm++)
						{
							if((gameboard[roww-1][colm+1]==-1 || gameboard[roww-1][colm]==-1) && gameboard[roww][colm]==0)
							{
								tempcol=colm;;
								break;
							}
						}
					}
					nodeone[0]=roww;
					nodeone[1]=tempcol;
					//System.out.println("**nodeone = "+nodeone[0]+" "+nodeone[1]);
                    roww=11;
                    colm=1;
					for(roww = 11;roww>0;roww--)
					{
						for(colm=1;colm<12;colm++)
						{
							//System.out.println("gameboard["+roww+"]["+colm+"] = "+gameboard[roww][colm]);
							if(gameboard[roww][colm]==0)
								break;
						}
						if(colm<12)
							break;
					}
					tempcol=colm;
					//System.out.println("tempcol"+tempcol);
					if (roww!=11)
					{	
					for(colm=1;colm<12;colm++)
						{
							if((gameboard[roww+1][colm-1]==-1 || gameboard[roww+1][colm]==-1) && gameboard[roww][colm]==0)
							{
								tempcol=colm;
								break;
							}
						}
					}
					nodetwo[0]=roww;
					nodetwo[1]=tempcol;
					//System.out.println("**nodeone = "+nodetwo[0]+" "+nodetwo[1]);
	
					boolean canbridgeup=true, canbridgedown=true;
					int[][] mat=new int[10][5];
					mat=prioritymatric(player,nodetwo[0],nodetwo[1]);
					//System.out.println("*prioritymatric returned*"+mat[0][0]+" "+mat[0][1]+mat[0][2]+"**"+mat[0][3]);	
					//System.out.println("pnode = "+mat[0][3]);
					
					if (mat[0][4]!=0 && !printed)
					{
					   for (int i=0;i<=mat[0][4] ; i++)
					   {
					   	   // System.out.println("*for loop i= *"+i+" "+mat[i][0]+" "+mat[i][1]+" "+mat[i][2]+" "+mat[i][3]+"**");	
						    if (mat[i][0]==1 && (gameboard[mat[i][1]-1][mat[i][2]+1]!=player && gameboard[mat[i][1]-1][mat[i][2]]!=player))
					     	{
							    canbridgeup=false;
							    canbridgedown=false;
							  //  System.out.println("its here in modification");
 							    if (gameboard[mat[i][1]-1][mat[i][2]+1]==-1)
 							    {
 								     //gameboard[mat[i][1]][mat[i][2]-1]=player;
 								     System.out.print(inttochar(mat[i][1]));
				                     System.out.print(mat[i][2]+1);
				                     printed=true;
 							    }
 						     	else
 							    {
 							        //gameboard[mat[i][1]+1][mat[i][2]-1]=player;	
 							        System.out.print(inttochar(mat[i][1]-1));
				                     System.out.print(mat[i][2]);
				                     printed=true;
 						    	}
					    	}
				   	    }
					
						
					}
					if(nodeone[0]<=2)
						canbridgeup = false;
				//	System.out.println("**********canbridgeup==="+canbridgeup);
					if(nodetwo[0]>9)
						canbridgedown = false;
				//	System.out.println("***********canbridgedown==="+canbridgedown);
					int[] resultbridge = new int[4];
					/*System.out.println(nodeone[0]);
					System.out.print(" "+nodeone[1]);
					System.out.print(" "+nodetwo[0]);
					System.out.print(" "+nodetwo[1]);	*/
					boolean m1,m2,m3,m4,m5,m6;
					m1=validatemove(nodeone[0]-1,nodeone[1]);
                    m2=validatemove(nodeone[0]-1,nodeone[1]+1);
                    m3=validatemove(nodetwo[0]+1,nodetwo[1]-1);
                    m4=validatemove(nodetwo[0]+1,nodetwo[1]);
                    int mus=verifypath(player,nodeone[0],nodeone[1],1);
                    int mus2=verifypath(player,nodetwo[0],nodetwo[1],2);
                //    System.out.println("mus and mus2 = "+mus+mus2);

                    if(!printed)
                    {
                    if (mus!=-1 && (m1 || m2))
                    {
                    	/*int mus2,mus3;
                    	mus2=verifybridge2();
                    	mus3=verifybridge2();
                    	*/
                        
                        	if(m1)
							{


								System.out.print(inttochar(nodeone[0]-1));
				           		System.out.print(nodeone[1]);
				           		printed=true;
							}
					    	else if(m2)
							{
								System.out.print(inttochar(nodeone[0]-1));
				            	System.out.print(nodeone[1]+1);
				            	printed=true;
							}
					}
					else if (mus2!=-1 && (m3 || m4))
					{
					        if(m3)
							{
								System.out.print(inttochar(nodetwo[0])+1);
				            	System.out.print(nodetwo[1]-1);
				            	printed=true;
							}
							else if(m4)
							{
								System.out.print(inttochar(nodetwo[0]+1));
				            	System.out.print(nodetwo[1]);
				            	printed=true;
							}
					}	

                    else if(m1 && gameboard[nodeone[0]-1][nodeone[1]+1]==player2)
						{
					//		System.out.println("here1");
							System.out.print(inttochar(nodeone[0]-1));
				            System.out.print(nodeone[1]);
				            printed=true;
						}
					else if(m2 && gameboard[nodeone[0]-1][nodeone[1]]==player2)
						{
					//		System.out.println("here2");
							System.out.print(inttochar(nodeone[0]-1));
				            System.out.print(nodeone[1]+1);
				            printed=true;
						}
					
					else if(m3 && gameboard[nodetwo[0]+1][nodetwo[1]]==player2)
						{
					//		System.out.println("here3");
							System.out.print(inttochar(nodetwo[0]+1));
				            System.out.print(nodetwo[1]-1);
				            printed=true;
						}
					else if(m4 && gameboard[nodetwo[0]+1][nodetwo[1]-1]==player2)
						{
					//	    System.out.println("here4");
							System.out.print(inttochar(nodetwo[0]+1));
				            System.out.print(nodetwo[1]);
				            printed=true;
						}
				    else if (!m1 && !m2 && nodeone[0]!=1)
				    {
				    //	System.out.println("possibility 1");
				    	int x2=nodeone[0];
				    	int y2=nodeone[1];
				    	int[] changeco=new int[2];

                        if (gameboard[x2+1][y2-1]==player)
                        {
                            changeco=newbridge(player,x2+1,y2-1);
                            if (validatemove(changeco[0],changeco[1])) {
                            System.out.print(inttochar(changeco[0]));
				            System.out.print(changeco[1]);
				            printed=true;                            	
                            }
                            else{
                            	printed=false;
                            }


                        }
                        else if (gameboard[x2+1][y2]==player)
                        {
                        	changeco=newbridge(player,x2+1,y2);
                        	
				            if (validatemove(changeco[0],changeco[1])) {
                            System.out.print(inttochar(changeco[0]));
				            System.out.print(changeco[1]);
				            printed=true;                            	
                            }
                            else{
                            	printed=false;
                            }
                        }
				        
				    }
				    else if (!m3 && !m4 && nodetwo[0]!=11)
				    {
				    //	System.out.println("possibility 2");
				    	int x2=nodetwo[0];
				    	int y2=nodetwo[1];
				    	int[] changeco=new int[2];
						if (gameboard[x2-1][y2+1]==player)
                        {
                            changeco=newbridge(player,x2-1,y2+1);
                            System.out.print(inttochar(changeco[0]));
				            System.out.print(changeco[1]);
				            printed=true;
                        }
                        else if (gameboard[x2-1][y2]==player)
                        {
                        	changeco=newbridge(player,x2-1,y2);
                        	System.out.print(inttochar(changeco[0]));
				            System.out.print(changeco[1]);
				            printed=true;
                        }
                    }
					else if((m1 && m2) && canbridgeup)
					{					
						{
                      //      System.out.println("canbridgeup");
                            // 	System.out.print(nodeone[0]+"**"+nodeone[1]);
							resultbridge = bridge(0,0,nodeone[0],nodeone[1]);
							System.out.print(inttochar(resultbridge[0]));
				            System.out.print(resultbridge[1]);
				            printed=true;
						}
					}
					else if((m3 && m4) && canbridgedown)
					{
						//System.out.println("it is here baby");
						{
							resultbridge = bridge(0,1,nodetwo[0],nodetwo[1]);
							System.out.print(inttochar(resultbridge[0]));
				            System.out.print(resultbridge[1]);
				            printed=true;
						}
					}
					else if(m1 && m2)
						{
						//	System.out.println("here5");
							System.out.print(inttochar(nodeone[0]-1));
				            System.out.print(nodeone[1]+1);
				            printed=true;
						}
					/*else if(m2)
						{
							System.out.print(inttochar(nodeone[0]+1));
				            System.out.print(nodeone[1]-1);
						}*/
					
					else if(m3 && m4)
						{
						//	System.out.println("here6");
							System.out.print(inttochar(nodetwo[0]+1));
				            System.out.print(nodetwo[1]-1);
				            printed=true;
						}
					/*else if(m4 )
						{
							System.out.print(inttochar(nodetwo[0]-1));
				            System.out.print(nodetwo[1]+1);
						}*/
					else if (!printed) 
					{
						//System.out.println("here7");
						int q=mat[0][4];
						//System.out.println("q= "+q);
						if(q>0)
						{
                             while(mat[q-1][0]!=0)
                             {
                             	q--;
                             }
                             if (mat[q-1][3]==0) {
                                int x1=mat[q-1][1]-1;
                                int y1=mat[q-1][2];
                                System.out.print(inttochar(x1));
	     			             System.out.print(y1);
	     			             printed=true;

                             }
                             else if (mat[q-1][3]==1) {
                             	 int x1=mat[q-1][1]-1;
                                int y1=mat[q-1][2];
                                System.out.print(inttochar(x1));
	     			             System.out.print(y1);
	     			             printed=true;
                             	
                             }
                             else if (mat[q-1][3]==2) {
                             	 int x1=mat[q-1][1]-1;
                                int y1=mat[q-1][2]+1;
                                System.out.print(inttochar(x1));
	     			             System.out.print(y1);
	     			             printed=true;
                             	
                             }

                        
						}
					}
					if (!printed)
					{
			           //System.out.println("in the randon fn");
	
						int[] randomresu = new int[3];
						randomresu = randommove();
						System.out.print(inttochar(randomresu[0]));
						System.out.print(randomresu[1]);
						printed=true;
    			    }    
				    
				}	
				}
			}
        }
    }


    public static int verifypath(int p, int rough, int colum,int d)
	{
		int r = rough;
		int c = colum;
		if(p==1)
		{
			while (d==1 && c>=1 && r>0 && r<12) 
			{
				if(gameboard[r][c]== 0)
				{
					return c;
				}
				else
				{
				//	System.out.println("+++++"+r+" "+c);
					r++;
					c-=2;
				}
			}
			r = rough;
		    c = colum;
			while (d==2 && c<=11 && r>0 && r<12) 
			{
				if(gameboard[r][c]== 0)
					return c;
				else
				{
				//	System.out.println("+++++"+r+" "+c);
					r--;
					c+=2;
				}
			}
		}
		else
		{
			while (d==1 && r>=1 && c>0 && c<12) 
			{
				if(gameboard[r][c]== 1)
				{
					return r;
				}
				else
				{
				//	System.out.println("+++++"+r+" "+c);
					r-=2;
					c++;
				}
			}
			r = rough;
		    c = colum;
			while (d==2 && r<=11 && c>0 && c<12) 
			{
				if(gameboard[r][c]== 1)
					return r;
				else
				{
				//	System.out.println("+++++"+r+" "+c);
					c--;
					r+=2;
				}
			}
		}
		return -1;
	}

    public static int[] bridge(int p,int direc, int row, int col)
    {
    	int[] bridgeres= new int[4];
    	int rowc,colc;
        if(p==1)
        {
        	if(direc==0)
        	{
        		rowc=row+1;
        		colc = col-2;
        		if(validatemove(rowc,colc))
        		{
        			bridgeres[0]=rowc;
        			bridgeres[1]=colc;
        			return bridgeres;
        		}
        	}
        	else
        	{
        		rowc=row-1;
        		colc = col+2;
        		if(validatemove(rowc,colc))
        		{
        			bridgeres[0]=rowc;
        			bridgeres[1]=colc;
        			return bridgeres;
        		}
        	}
        }
        else
        {
        	/*bridgeres = randommove();
        	return bridgeres;*/
        	if(direc==0)
        	{
        		rowc=row-2;
        		colc = col+1;
        		if(validatemove(rowc,colc))
        		{
        			bridgeres[0]=rowc;
        			bridgeres[1]=colc;
        			return bridgeres;
        		}
        	}
        	else
        	{
        		rowc=row+2;
        		colc = col-1;
        		if(validatemove(rowc,colc))
        		{
        			bridgeres[0]=rowc;
        			bridgeres[1]=colc;
        			return bridgeres;
        		}
        	}
        }
       return bridgeres;
    }

    public static char inttochar(int x)
	{
		switch(x)
		{
			case 1: return 'A';
					///break;
			case 2: return 'B';
					//break;
			case 3: return 'C';
					//break;
			case 4: return 'D';
					//break;
			case 5: return 'E';
					//break;
			case 6: return 'F';
					//break;
			case 7: return 'G';
					//break;
			case 8: return 'H';
					//break;
			case 9: return 'I';
					//break;
			case 10: return 'J';
					//break;
			case 11: return 'K';
					//break;
			default: return '*';
					//break;				
		}
	}

	public static boolean validatemove(int r, int c)
	{
		if(r>0 && r<12 && c>0 && c<12)
		{if(gameboard[r][c]==-1 )//&& r>0 && r<12 && c>0 && c<12)
			return true;
			return false;
		}
		return false;
	}

	public static int[] randommove()
	{
		Random r=new Random();
		int low=2;
		int high =10;
		int[] randomres = new int[3];
		while (true) 
		{
			randomres[0] = r.nextInt(high-low+1) + low;
			randomres[1] = r.nextInt(high-low+1) + low;
			if(validatemove(randomres[0],randomres[1]))
				break;
		}
		return randomres;
	}
	
    public static int[] newbridge(int p,int x,int y)
    {
    	int[] a = new int[3];
        int[] res = new int[3];
    	if (p==1)
    	{

            a[0]=gameboard[x+1][y-2];
            a[1]=gameboard[x+2][y-1];
            a[2]=gameboard[x-1][y-1];
            for (int i=0;i<3 ;i++ )
            {
            	if (a[i]==-1)
            	{
            		//System.out.println("abc");
            		switch(i)
            		{
            			case 0 : res[0]=x+1;
            		             res[1]=y-2;
            		             return res;
            			case 1 : res[0]=x+2;
            		             res[1]=y-1;
            		             return res;
            			case 2 : res[0]=x-1;
            		             res[1]=y-1;
            		             return res;
            		   
            		}

            	}
            	
            }
        }
        else
        {
            a[0]=gameboard[x-2][y+1];
            a[1]=gameboard[x-1][y-1];
            a[2]=gameboard[x-1][y+2];
            for (int i=0;i<3 ;i++ )
            {
            	if (a[i]==-1)
            	{
            		switch(i)
            		{
            			case 0 : res[0]=x-2;
            		             res[1]=y+1;
            		            // res[2]=0;
            		             return res;
            			case 1 : res[0]=x-1;
            		             res[1]=y-1;
            		            // res[2]=1;
            		             return res;
            			case 2 : res[0]=x-1;
            		             res[1]=y+2;
            		            // res[2]=2;
            		             return res;
            		   
            		}
            	}
            	/*else if (i==2)
            	{
            		res[0]=-1;
            	 	res[1]=-1;
            		res[2]=-1;
            		return res;
            	}*/
            }
        }
        return res;
    }

	public static int[][] prioritymatric(int p, int r, int c)
	{
		int[] currnode = new int[3];
		int[] adjecncy = new int[3];
		int pnode=0;
		currnode[0]=r;
		currnode[1]=c;
        int count =0;
		while(currnode[0]!=nodeone[0] && currnode[1]!=nodeone[1] && count<100)
		{
			int[] temp = new int[3];
			count++;
			//System.out.println("temp1***"+currnode[0]+" "+currnode[1]);
			temp = verifybridge(p,currnode[0],currnode[1]);
			//System.out.println("temp1***"+currnode[0]+" "+currnode[1]);

			//System.out.println("*temp2*"+temp[0]+" "+temp[1]+"**");
			if(p==1)
			{
				if(temp[0]==-1)
				{
					
					adjecncy[0]=gameboard[currnode[0]+1][currnode[1]-1];
	            	adjecncy[1]=gameboard[currnode[0]][currnode[1]-1];
			//		System.out.println("*adjecncy*"+adjecncy[0]+" "+adjecncy[1]+"**");	            	
	            	for (int iter=0;iter<2 ;iter++ ) 
	            	{
	            		if(adjecncy[iter]==1)
	            		{
	            			if(iter==0)
	            			{
	            				currnode[0] = currnode[0]+1;
	            				currnode[1] = currnode[1]-1;
	            			}
	            			else
	            			{
	            				currnode[0] = currnode[0];
	            				currnode[1] = currnode[1]-1;
	            			}
	            		}
	            	}
				}
				else
				{
					if (temp[2]==0) {
					adjecncy[0]=gameboard[currnode[0]+1][currnode[1]-1];
	            	adjecncy[1]=gameboard[currnode[0]][currnode[1]-1];
	            	}
	            	else if (temp[2]==1) {
	            	adjecncy[0]=gameboard[currnode[0]+1][currnode[1]-1];
	            	adjecncy[1]=gameboard[currnode[0]+1][currnode[1]];
	            	}
	            	else if (temp[2]==2) {
	            		adjecncy[0]=gameboard[currnode[0]-1][currnode[1]];
	            	adjecncy[1]=gameboard[currnode[0]][currnode[1]-1];
	            	
	            	}
	          //  	System.out.println("*adjecncy else*"+adjecncy[0]+" "+adjecncy[1]+"**");	
	            	if (adjecncy[0]==-1 && adjecncy[1]==-1)
	        		{
	        			priorityres[pnode][0]=0;
	        		}
	        		else if((adjecncy[0]==-1 && adjecncy[1]==1) || (adjecncy[0]==-1 && adjecncy[1]==0) || (adjecncy[0]==1 && adjecncy[1]==-1) || (adjecncy[0]==0 && adjecncy[1]==-1))
	        		{
	        			priorityres[pnode][0]=1;
	        		}
	        		else
	        		{
	        			priorityres[pnode][0]=2;
	        		}

	        		priorityres[pnode][1]=currnode[0];
	        		priorityres[pnode][2]=currnode[1];
	        		priorityres[pnode][3]=temp[2];
	                currnode[0]=temp[0];
	                currnode[1]=temp[1];
	            //    System.out.println("*Now curr*"+currnode[0]+" "+currnode[1]+"**");
	                priorityres[0][4] = pnode+1;	
	                pnode++;
	              //  			System.out.println("*pnode =*"+pnode+"**");	
				}
			}
			else
			{
				if(temp[0]==-1)
				{
					
					adjecncy[0]=gameboard[currnode[0]-1][currnode[1]+1];
	            	adjecncy[1]=gameboard[currnode[0]-1][currnode[1]];
					//System.out.println("*adjecncy*"+adjecncy[0]+" "+adjecncy[1]+"**");	            	
	            	for (int iter=0;iter<2 ;iter++ ) 
	            	{
	            		if(adjecncy[iter]==0)
	            		{
	            			if(iter==0)
	            			{
	            				currnode[0] = currnode[0]-1;
	            				currnode[1] = currnode[1]+1;
	            			}
	            			else
	            			{
	            				currnode[0] = currnode[0]-1;
	            				currnode[1] = currnode[1];
	            			}
	            		}
	            	}
				}
				else
				{
					if (temp[2]==0) {
					adjecncy[0]=gameboard[currnode[0]-1][currnode[1]+1];
	            	adjecncy[1]=gameboard[currnode[0]-1][currnode[1]];
	            	}
	            	else if (temp[2]==1) {
	            	adjecncy[0]=gameboard[currnode[0]-1][currnode[1]];
	            	adjecncy[1]=gameboard[currnode[0]][currnode[1]-1];
	            	}
	            	else if (temp[2]==2) {
	            		adjecncy[0]=gameboard[currnode[0]-1][currnode[1]+1];
	            	adjecncy[1]=gameboard[currnode[0]][currnode[1]+1];
	            	
	            	}
	            	//System.out.println("*adjecncy else*"+adjecncy[0]+" "+adjecncy[1]+"**");	
	            	if (adjecncy[0]==-1 && adjecncy[1]==-1)
	        		{
	        			priorityres[pnode][0]=0;
	        		}
	        		else if((adjecncy[0]==-1 && adjecncy[1]==1) || (adjecncy[0]==-1 && adjecncy[1]==0) || (adjecncy[0]==1 && adjecncy[1]==-1) || (adjecncy[0]==0 && adjecncy[1]==-1))
	        		{
	        			priorityres[pnode][0]=1;
	        		}
	        		else
	        		{
	        			priorityres[pnode][0]=2;
	        		}

	        		priorityres[pnode][1]=currnode[0];
	        		priorityres[pnode][2]=currnode[1];
	        		priorityres[pnode][3]=temp[2];
	                currnode[0]=temp[0];
	                currnode[1]=temp[1];
	                //System.out.println("*Now curr*"+currnode[0]+" "+currnode[1]+"**");
	                priorityres[0][4] = pnode+1;	
	                pnode++;
	                //			System.out.println("*pnode =*"+pnode+"**");	
				}
			}
		}
		return priorityres;
	}

	public static int[] verifybridge(int p,int x,int y)
    {
    	int[] a = new int[3];
        int[] res = new int[3];
    	if (p==1)
    	{

            a[0]=gameboard[x+1][y-2];
            a[1]=gameboard[x+2][y-1];
            a[2]=gameboard[x-1][y-1];
            for (int i=0;i<3 ;i++ )
            {
            	if (a[i]==1)
            	{
            		//System.out.println("abc");
            		switch(i)
            		{
            			case 0 : res[0]=x+1;
            		             res[1]=y-2;
            		             res[2]=0;
            		             return res;
            			case 1 : res[0]=x+2;
            		             res[1]=y-1;
            		             res[2]=1;
            		             return res;
            			case 2 : res[0]=x-1;
            		             res[1]=y-1;
            		             res[2]=2;
            		             return res;
            		   
            		}

            	}
            	else if (i==2){
            		 res[0]=-1;
            		  res[1]=-1;
            		  res[2]=-1;
            		  return res;
            	}
            }
        }
        else
        {
            //int a[3],res[2];
            a[0]=gameboard[x-2][y+1];
            a[1]=gameboard[x-1][y-1];
            a[2]=gameboard[x-1][y+2];
            for (int i=0;i<3 ;i++ )
            {
            	if (a[i]==0)
            	{
            		switch(i)
            		{
            			case 0 : res[0]=x-2;
            		             res[1]=y+1;
            		             res[2]=0;
            		             return res;
            			case 1 : res[0]=x-1;
            		             res[1]=y-1;
            		             res[2]=1;
            		             return res;
            			case 2 : res[0]=x-1;
            		             res[1]=y+2;
            		             res[2]=2;
            		             return res;
            		   
            		}
            	}
            	else if (i==2)
            	{
            		res[0]=-1;
            	 	res[1]=-1;
            		res[2]=-1;
            		return res;
            	}
            }
        }
        return res;
    }
}
	