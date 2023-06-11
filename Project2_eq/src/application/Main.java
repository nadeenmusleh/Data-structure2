  package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		CursorStack FileStack = new CursorStack();

		BorderPane pane = new BorderPane();

		Button load = new Button("load");
		load.setStyle("-fx-background-color: lightskyblue");
		load.setPrefSize(50, 10);

		Button back = new Button("Back");
		back.setPrefSize(50, 10);

		Label F1 = new Label();
		F1.setPrefSize(355, 25);

		HBox hbox = new HBox(50, back, F1, load);
		hbox.setPadding(new Insets(10,10,10,10));

		TextArea equation = new TextArea();
		equation.setPrefSize(250, 100);

		TextArea files = new TextArea();
		files.setPrefSize(300, 100);

		Text equations = new Text("equations");
		equations.setTranslateY(50);
		equations.setTranslateX(-200);
		equations.setFont(Font.font("Times Roman", FontWeight.BOLD, 40));
		equations.setFill(Color.BLACK);

		VBox t = new VBox();
		Text Files = new Text("Files");
		Files.setTranslateY(50);
		Files.setTranslateX(-200);
		Text title = new Text("equations ");
		title.setFont(Font.font("Times new Roman", FontWeight.BOLD, 28));
		title.setFill(Color.WHITE);
		t.getChildren().addAll(title);
		t.setAlignment(Pos.CENTER);
		t.setSpacing(5);
		t.setTranslateY(10);

		pane.setStyle("-fx-background-color: aliceblue");
		back.setStyle("-fx-background-color: lightskyblue");

		F1.setStyle("-fx-background-color: lightskyblue");
		equations.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
		Files.setFont(Font.font("Tahoma", FontWeight.BOLD, 11));
		F1.setStyle("-fx-border-color:darkblue");

		VBox vbox = new VBox(20, equations, equation, Files, files);
        
    	FileChooser fileChooser = new FileChooser();

        load.setOnMouseClicked(e -> {
        	File selectedFile = fileChooser.showOpenDialog(primaryStage);
        	String currentPath = Paths.get("C:\\data").toAbsolutePath().normalize().toString();

        	equation.setText("");

        	String line="";
        	String line2="";
        	F1.setText(selectedFile.getPath());

			if (selectedFile != null) {

				String filePath = selectedFile.getPath();
				files.setText(selectedFile.getName()+"\n");
				FileStack.push(filePath);
				try {

					BufferedReader br = new BufferedReader(new FileReader(filePath));
					StringBuilder sb = new StringBuilder();
					
					while ((line = br.readLine()) != null) {
	if (line.contains("<file>") && line.endsWith("</file>")) {
							
							
							String[] splitOne = line.split("<file>");
							String[] splitTwo = splitOne[1].split("</file>");
							String filess = new String(splitTwo[0]);
							File f = new File(filess);
							String parent = f.getPath();
							files.appendText(f.getName()+"\n");
				
							
							
							try {
							BufferedReader br1 = new BufferedReader(new FileReader(parent));
							StringBuilder sb1 = new StringBuilder();
							while ((line2 = br1.readLine()) != null) {
								if (line2.contains("<equation>") && line2.endsWith("</equation>")) {
									String[] splitOne1 = line2.split("<equation>");
									String[] splitTwo2 = splitOne1[1].split("</equation>");
									String fileequ = new String(splitTwo2[0]);
								
									
									String str1 = infixToPostfix(fileequ);
						
		                            if(isBalanced(fileequ)) {
		                                 if(str1 == "false") {
		                                		equation.appendText(fileequ +"=>"+ "invalid Equation" +"\n");
		                                	continue;
		                                 }
									equation.appendText(fileequ +"=>"+ str1  + "=>" + result(str1) +"\n");
		                            }
		                            else if(!isBalanced(fileequ)) {
		                            	equation.appendText(fileequ +"=>"  +"Unbalanced Equation" +"\n");
		                            }
		                            if(isValid(fileequ)) {
		                                 if(str1 == "false") {
		                                		equation.appendText(fileequ +"=>"+ "invalid File" +"\n");
		                                	continue;
		                                 }
									equation.appendText(fileequ +"=>"+ str1  + "=>" + result(str1) +"\n");
		                            }
		                            else if(!isValid(fileequ)) {
		                            	equation.appendText(fileequ +"=>"  +"invalid file" +"\n");
		                            }
									
									
								}else if(line.contains("<equation>") && !line.endsWith("</equation>")) {
									equation.appendText("invalid File");
								}
							}
							
							
							}catch (IOException e1) {

							}
							
							
							
                           
						}
	else if(line.contains("<file>") && !line.endsWith("</file>")) {
		equation.appendText("invalid File");
	}
	
						if (line.contains("<equation>") && line.endsWith("</equation>")) {
							String[] splitOne = line.split("<equation>");
							String[] splitTwo = splitOne[1].split("</equation>");
							String fileequ = new String(splitTwo[0]);
						
							
							String str = infixToPostfix(fileequ);
				
                            if(isBalanced(fileequ)) {
                                 if(str == "false") {
                                		equation.appendText(fileequ +"=>"+ "invalid Equation" +"\n");
                                	continue;
                                 }
							equation.appendText(fileequ +"=>"+ str  + "=>" + result(str) +"\n");
                            }
                            else if(!isBalanced(fileequ)) {
                            	equation.appendText(fileequ +"=>"  +"Unbalanced Equation" +"\n");
                            }
							
							
						}else if(line.contains("<equation>") && !line.endsWith("<equation>")) {
							equation.appendText("invalid File");
						}
						
					
//						else {
//							fillesfield.getItems().add("no files");
//
//						}
					}
					// print ll
				} catch (IOException e1) {

				}
			}
			 
		});
        back.setOnAction(e->{
      	File selectedFile = new File((String) FileStack.peek());
//
        	equation.setText("");
        	files.setText("");
        	if(FileStack.isEmpty()) {
        	
        		return;
        		
        	}
        	
        	
        	if(!FileStack.isEmpty()) {
        		FileStack.pop();
        		F1.setText(FileStack.peek().toString());
        		Object prev = FileStack.peek();
        		String line="";
        	   	String line2="";

        		
    			if ( prev!= null) {

    				String filePath = (String) prev;
    				String str1 = filePath.substring(filePath.lastIndexOf("f"));
    				files.setText(str1+"\n");

                        
    				
    				try {

    					BufferedReader br = new BufferedReader(new FileReader(filePath));
    					StringBuilder sb = new StringBuilder();
    					
    					while ((line = br.readLine()) != null) {
    						if (line.contains("<file>") && line.endsWith("</file>")) {
    							
//    							ListView <String> fillesfield=new ListView();
    							
    							String[] splitOne = line.split("<file>");
    							String[] splitTwo = splitOne[1].split("</file>");
    							String filess = new String(splitTwo[0]);
    							File f = new File(filess);
    							String parent = f.getPath();
    							files.appendText(f.getName()+"\n");

    							try {
    							BufferedReader br1 = new BufferedReader(new FileReader(parent));
    							StringBuilder sb1 = new StringBuilder();
    							while ((line2 = br1.readLine()) != null) {
    								if (line2.contains("<equation>") && line2.endsWith("</equation>")) {
    									String[] splitOne1 = line2.split("<equation>");
    									String[] splitTwo2 = splitOne1[1].split("</equation>");
    									String fileequ = new String(splitTwo2[0]);
    								
    									
    									String str2 = infixToPostfix(fileequ);
    						
    		                            if(isValid(fileequ)) {
    		                                 if(str2 == "false") {
    		                                		equation.appendText(fileequ +"=>"+ "invalid Equation" +"\n");
    		                                	continue;
    		                                 }
    									equation.appendText(fileequ +"=>"+ str2  + "=>" + result(str2) +"\n");
    		                            }
    		                            else if(!isBalanced(fileequ)) {
    		                            	equation.appendText(fileequ +"=>"  +"Unbalanced Equation" +"\n");
    		                            }
    									
    									
    								}else if(line.contains("<equation>") && !line.endsWith("<equation>")) {
    									equation.appendText("invalid File");
    								}
    							}
    							
    							}catch (IOException e1) {

    							}
    							
//                                fillesfield.getItems().add(parent);
                               
    						}else if(line.contains("<file>") && !line.endsWith("<file>")) {
								equation.appendText("invalid File");
							}
    						if (line.contains("<equation>") && line.endsWith("</equation>")) {
    							String[] splitOne = line.split("<equation>");
    							String[] splitTwo = splitOne[1].split("</equation>");
    							String fileequ = new String(splitTwo[0]);
    						
    							
    							String str = infixToPostfix(fileequ);
    						

    							 if(isBalanced(fileequ)) {
    								  if(str == "false"){
                                   		equation.appendText(fileequ +"=>"+ "invalid Equation" +"\n");
                                   		
                                   		continue;
                                   		}
    									equation.appendText(fileequ +"=>"+ str  + "=>" + result(str) +"\n");
    		                            }
    		                            else if(!isBalanced(fileequ)) {
    		                            	equation.appendText(fileequ +"=>"+"Unbalanced Equation" +"\n");
    		                            }
    							
    							
    							
    						}else if(line.contains("<equation>") && !line.endsWith("<equation>")) {
								equation.appendText("invalid File");
							}
    					}
    				}
							
    					
    				
    				catch (IOException e2) {

    				}
    			}
          
        	}
//        	files.setText((String) FileStack.peek().toString());
//        	equation.setText((String) FileStack.peek().toString());
        	

        });
        ListView FilesArea=new ListView();
        FilesArea.setOnMousePressed(e->{
        	
        	FileStack.push(FilesArea.getSelectionModel().getSelectedItem());
        	FilesArea.getItems().clear();
        	File SelectedFile=new File((String) FileStack.peek());
        	files.setText(SelectedFile.getPath());
        	FileStack.push(SelectedFile.getPath());
        	
        });
       
        hbox.setAlignment(Pos.TOP_LEFT);
        pane.setTop(hbox);
        vbox.setAlignment(Pos.CENTER);
        pane.setCenter(vbox);
		Scene scene1=new Scene(pane);
		primaryStage.setScene(scene1);
		primaryStage.show();
	}
	private static boolean isBalanced(String expression) {
		char[] chars = expression.toCharArray();
		CursorStack stack = new CursorStack();
		for (char c : chars) {
			if (c == '(' || c == '{' || c == '[') {
				stack.push(c);
			} else if (c == ')' || c == '}' || c == ']') {
				if (stack.isEmpty()) {
					return false;
				}
				char top = (char) stack.pop();
				if (c == ')' && top != '(') {
					return false;
				} else if (c == '}' && top != '{') {
					return false;
				} else if (c == ']' && top != '[') {
					return false;
				}
			}
		}
		return stack.isEmpty();
	}
        
        static int Prec(char ch)
        {
            switch (ch) {
            case '+':
            case '-':
                return 1;
     
            case '*':
            case '/':
                return 2;
     
            case '^':
                return 3;
            }
            return -1;
        }
	
     
        // The main method that converts
     // given infix expression
        // to postfix expression.
        static String infixToPostfix(String exp)
        {
            // initializing empty String for result
            String result = new String("");
            // initializing empty stack
            CursorStack stack=new CursorStack();
            int counter1 = 0;
            int counter2 = 0;
     for(int j=0;j<exp.length();j++) {
    	if(Character.isDigit(exp.charAt(j))) {
    		if(j<exp.length()-2 && Character.isDigit(exp.charAt(j+1))) {
    			continue;
    		}
    		counter1++;
    		continue;
    	}
    	if(exp.charAt(j)==')'||exp.charAt(j)=='(' || exp.charAt(j)==' ') {
    		continue;
    	}
    	counter2++;
    		
     }
     if(counter2>=counter1) {
    	 return "false";
     }
            for (int i = 0; i < exp.length(); ++i) {
                char c = exp.charAt(i);
     if(c==' ') {
    	 continue;
     }
                // If the scanned character is an
                // operand, add it to output.
                if (i<= exp.length()-2 && Character.isLetterOrDigit(c) && Character.isDigit(exp.charAt(i+1))) {
                    result += c ;
                    result += exp.charAt(i+1)  +" ";
                     i++;
                }
                // If the scanned character is an '(',
                // push it to the stack.
                else if(Character.isLetterOrDigit(c)) {
                	 result += c+" ";
                }
                else if (c == '(')
                    stack.push(c);
     
                //  If the scanned character is an ')',
                // pop and output from the stack
                // until an '(' is encountered.
              
                else if (c == ')') {
                    while (!stack.isEmpty()
                           && (char)stack.peek() != '(') {
                        result += stack.peek()+" ";
                        stack.pop();
                    }
     
                    stack.pop();
                }
                else // an operator is encountered
                {
                    while (!stack.isEmpty()
                           && Prec(c) <= Prec((char)stack.peek())) {
     
                        result += stack.peek()+" ";
                        stack.pop();
                    }
                   stack.push(c);
           
                }
            }
     
            // pop all the operators from the stack
            while (!stack.isEmpty()) {
                if ((char)stack.peek() == '(')
                    return "Invalid Expression";
                result += stack.peek()+" ";
                stack.pop();
            }
           
            return result;
        
    
       
        	
        	
        }	
        public static boolean isValid(String line) {
    		if ((line.contains("<242>")) && !(line.contains("</242>"))) {
    			return false;
    		} else if ((line.contains("<equations>")) && !(line.contains("</equations>"))) {
    			return false;
    		} else if ((line.contains("<files>")) && !(line.contains("</files>"))) {
    			return false;
    		} else if ((line.contains("<equation>")) && !(line.contains("</equation>"))) {
    			return false;
    		} else if ((line.contains("<file>")) && !(line.contains("</file>"))) {
    			return false;
    		} else if ((line.contains("</242>")) && !(line.contains("<242>"))) {
    			return false;
    		} else if ((line.contains("</equations>")) && !(line.contains("<equations>"))) {
    			return false;
    		} else if ((line.contains("</files>")) && !(line.contains("<files>"))) {
    			return false;
    		} else if ((line.contains("</equation>")) && !(line.contains("<equation>"))) {
    			return false;
    		} else if ((line.contains("<f/ile>")) && !(line.contains("<file>"))) {
    			return false;
    		} else {
    			return true;
    		}
    	}
        public double result(String s) {
    		CursorStack stack = new CursorStack();
    		String[] tokens = s.split(" ");
    		double res;
    		for (int i = 0; i < tokens.length; i++)
    		   if(!(tokens[i]==""))
    				switch (tokens[i]) {
    				case "+":
    					res = Double.parseDouble((String) stack.pop()) + Double.parseDouble((String) stack.pop());
    					stack.push(Double.toString(res));
    					break;
    				case "-":
    					res = -Double.parseDouble((String) stack.pop()) + Double.parseDouble((String) stack.pop());
    					stack.push(Double.toString(res));
    					break;
    				case "/":
    					double a1 = Double.parseDouble((String) stack.pop());
    					double a2 = Double.parseDouble((String) stack.pop());
    					res = (a2 / a1);
    					stack.push(Double.toString(res));
    	    						
    		        	    break;
    					
    				case "*":
    					res = Double.parseDouble((String) stack.pop()) * Double.parseDouble((String) stack.pop());
    					stack.push(Double.toString(res));

    					break;
    				case "^":
    					double a4 = Double.parseDouble((String) stack.pop());
    					double a5 = Double.parseDouble((String) stack.pop());
    					res = Math.pow(a5, a4);
    					stack.push(Double.toString(res));
    					break;
    				case "":
    					break;
    				default:
    				stack.push(tokens[i]);
				
    					break;
    				}

    		return res = Double.parseDouble((String) stack.pop());

    	}
	

		
	
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
