import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;

public class Frame extends DefaultTableColumnModel{

	private static final long serialVersionUID = 2596444820177449526L;
	public static String strLine = "";
	public static String finalHTML = "";
	public static String User = "";
	public static Color BG = new Color(0,0,0,255);
	public static Color AVATAR_BG = new Color(0,0,0,200);
	public static Color FG = Color.decode("#E1BB34");
	public static String URL_BASE = "http://hiscore.runescape.com/index_lite.ws?player=";
	public static String AVATAR_PATH = "http://services.runescape.com/m=avatar-rs/";
	public static String[][] rowData = {
		{""}
	};
	public static Object columnNames[] = { User, "Level", "Exp", "Rank" };
	public static JTable table;
	public static JScrollPane scrollPane;
	public static URL url, BG_Url;
	public static BufferedImage Avatar, BackGround;
	public static JLabel Avatar_Label;
	public static JPanel CharAvatarPane = new JPanel();
	public static int Delay = 60;
	public static JFrame frame;

	public static String[] Stats = {"NULL"};

	public static String CheckChars(String User2){

		User2 = User2.toLowerCase().replaceAll("_","").replaceAll("-", "").replaceAll(" ", "")
				.replaceAll("a", "").replaceAll("b", "").replaceAll("c", "").replaceAll("d", "")
				.replaceAll("e", "").replaceAll("f", "").replaceAll("g", "").replaceAll("h", "")
				.replaceAll("i", "").replaceAll("j", "").replaceAll("k", "").replaceAll("l", "")
				.replaceAll("m", "").replaceAll("n", "").replaceAll("o", "").replaceAll("p", "")
				.replaceAll("q", "").replaceAll("r", "").replaceAll("s", "").replaceAll("t", "")
				.replaceAll("u", "").replaceAll("v", "").replaceAll("w", "").replaceAll("x", "")
				.replaceAll("y", "").replaceAll("z", "").replaceAll("1", "").replaceAll("2", "")
				.replaceAll("3", "").replaceAll("4", "").replaceAll("5", "").replaceAll("6", "")
				.replaceAll("7", "").replaceAll("8", "").replaceAll("9", "").replaceAll("0", "");
		return User2;

	}
	public static void main(String args[]) {
		frame = new JFrame();
		User = JOptionPane.showInputDialog(frame, "What User do you wish to find?").toLowerCase();
		while((User.length() > 12) || (User == null) ||
				(User.length() <= 0)|| (CheckChars(User).length() >= 1) ||
				(User.startsWith("_")) || (User.startsWith("-"))){    		
			User = JOptionPane.showInputDialog(frame, "Invalid Name Format: What User do you wish to find?").toLowerCase();
		}
		while(User.toLowerCase().contains("mod ") || User.toLowerCase().equalsIgnoreCase("modgameblast"))
		{
			JOptionPane.showMessageDialog(null, "Reason: You cant search a J-mod! Lol They do not have REAL Stats anyways ;)", "Error: " + "You are forbidden to Search for \""+User+"\".", JOptionPane.INFORMATION_MESSAGE);
			User = JOptionPane.showInputDialog(frame, "Be serious now, What User do you wish to find?").toLowerCase();
			while((User.length() > 12) || (User == null) ||
					(User.length() <= 0)|| (CheckChars(User).length() >= 1) ||
					(User.startsWith("_")) || (User.startsWith("-"))){    		
				User = JOptionPane.showInputDialog(frame, "Invalid Name Format: What User do you wish to find?").toLowerCase();
			}
		}
		RefreshData(User);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setBackground(Color.white);
		frame.setForeground(FG);
		frame.setFont(new Font("Regular", Font.TRUETYPE_FONT, 12));
		frame.setSize(new Dimension(650,950));
		frame.setVisible(true);
		frame.setResizable(false);
	}
	public static void RefreshData(String Char){
		System.out.println("Attempting to Load User: \""+User+"\"");
		try {
			System.out.println("Attempting to Read Stats for: \""+User+"\"");
			URL url = new URL(URL_BASE+Char);
			URLConnection spoof;
			spoof = url.openConnection();
			spoof.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0;    H010818)" );
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));

			StringBuilder sb = new StringBuilder();
			char[] chars = new char[4*1024];
			int len;
			while((len = in.read(chars))>=0) {
				sb.append(chars, 0, len);
			}
			finalHTML = sb.toString();
			sb = null;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "<html><center>Error 404: Cabbages Not Found.<br>so...<br>Nothing interesting happens.", "Error: " + "Error 404.", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		/* 
		 * Order used in the Strings for below: Rank, Level, EXP
		 *  
		 * Totals, Attack, Def, Str, HP, Ranged, Pray,
		 * Mage, Cook, wc, fletch, fish, fire, craft,
		 * Smith, Mining, Herb, Agility, Thief, Slay,
		 * Farm, Rcing, Hunter, Con, Summon, Dung 
		 *
		 */
		Stats = finalHTML.replace("\n", ",").split(",");
		rowData = new String[][]{ 
				{ "Total:", Stats[1], Stats[2], Stats[0] },
				{ "Attack:", Stats[4], Stats[5], Stats[3] },
				{ "Defence:", Stats[7], Stats[8], Stats[6] },
				{ "Strength:", Stats[10], Stats[11], Stats[9] },
				{ "Constitution:", Stats[13], Stats[14], Stats[12] },
				{ "Ranged:", Stats[16], Stats[17], Stats[15] },
				{ "Prayer:", Stats[19], Stats[20], Stats[18] },
				{ "Magic:", Stats[22], Stats[23], Stats[21] },
				{ "Cooking:", Stats[25], Stats[26], Stats[24] },
				{ "Woodcutting:", Stats[28], Stats[29], Stats[27] },
				{ "Fletching:", Stats[31], Stats[32], Stats[30] },
				{ "Fishing:", Stats[34], Stats[35], Stats[33] },
				{ "Firemaking:", Stats[37], Stats[38], Stats[36] },
				{ "Crafting:", Stats[40], Stats[41], Stats[39] },
				{ "Smithing:", Stats[43], Stats[44], Stats[42] },
				{ "Mining:", Stats[46], Stats[47], Stats[45] },
				{ "Herblore:", Stats[49], Stats[50], Stats[48] },
				{ "Agility:", Stats[52], Stats[53], Stats[51] },
				{ "Thieving:", Stats[55], Stats[56], Stats[54] },
				{ "Slayer:", Stats[58], Stats[59], Stats[57] },
				{ "Farming:", Stats[61], Stats[62], Stats[60] },
				{ "RuneCrafting:", Stats[64], Stats[65], Stats[63] },
				{ "Hunter:", Stats[67], Stats[68], Stats[66] },
				{ "Construction:", Stats[70], Stats[71], Stats[69] },
				{ "Summoning:", Stats[73], Stats[74], Stats[72] },
				{ "Dungeoneering:", Stats[76], Stats[77], Stats[75] },
				{ "Divination:", Stats[79], Stats[80], Stats[78] },
		};
		String UserFormatted = User.replace(" ", "%A0");
		try {
			System.out.println("Attempting to Load Forum Avatar for: \""+User+"\"");
			url = new URL(AVATAR_PATH+UserFormatted+"/"+"chat.gif");
			Avatar = ImageIO.read(url);
			Avatar_Label = new JLabel(new ImageIcon(Avatar));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated cStats[4]atch block
			e.printStackTrace();
		}
		CharAvatarPane.removeAll();
		CharAvatarPane.add(Avatar_Label);
		table = new JTable(rowData, columnNames);

		/* Temp code used if i want to re-work on Recursive edits;
		 *
		 * for(int val = 0; val <=26; val++){
		 *	rowData[val][1] = Stats[1+(val*3)];
		 *	rowData[val][2] = Stats[2+(val*3)];
		 *	rowData[val][3] = Stats[0+(val*3)];
		 * }
		 *
		 */

		for(int ROW = 0; ROW <= 26; ROW++){
			table.setValueAt(rowData[ROW][0], ROW, 0);
			table.setValueAt(rowData[ROW][1], ROW, 1);
			table.setValueAt(rowData[ROW][2], ROW, 2);
			table.setValueAt(rowData[ROW][3], ROW, 3);
			table.setRowHeight(ROW, 30);
		}


		table.setDragEnabled(false);
		table.setCellSelectionEnabled(false);
		table.setEnabled(false);
		table.setFont(new Font("Regular", Font.TRUETYPE_FONT, 18));
		table.setBackground(BG);
		table.setForeground(FG);
		CharAvatarPane.setBackground(AVATAR_BG);
		scrollPane = new JScrollPane(table);
		frame.add(CharAvatarPane, BorderLayout.NORTH);
		frame.add(scrollPane, BorderLayout.CENTER);
		finalHTML = "";
		frame.setTitle("Currently Loaded: [ \""+User+"\" ]");
		System.out.println("Attempted Load Finished for: \""+User+"\"");
	}
}