/**
 * MediaFrame is an Open Source streaming media platform in Java 
 * which provides a fast, easy to implement and extremely small applet 
 * that enables to view your audio/video content without having 
 * to rely on external player applications or bulky plug-ins.
 * 
 * Copyright (C) 2004/5 MediaFrame (http://www.mediaframe.org).
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */
package mediaframe.mpeg4.video;

import java.io.IOException;

/**
 * Huffman
 */
public final class Huffman {

//	/** The length of max bitstring for the VLC table for MVD. */
//	public final static int MVD_TAB_LENGTH = 13;

	/** The VLC table for MVD. */
	public final static int[][] MVD_TAB = {
		{2, 1, 0},			// 0	
		{-1, -1, 0},		// 1	1
		{6, 3, 0},			// 2	0
		{5, 4, 0},			// 3	01
		{-1, -1, -1},		// 4	011
		{-1, -1, 1},		// 5	010
		{10, 7, 0},			// 6	00
		{9, 8, 0},			// 7	001
		{-1, -1, -2},		// 8	0011
		{-1, -1, 2},		// 9	0010
		{14, 11, 0},		// 10	000
		{13, 12, 0},		// 11	0001
		{-1, -1, -3},		// 12	00011
		{-1, -1, 3},		// 13	00010
		{26, 15, 0},		// 14	0000
		{19, 16, 0},		// 15	00001
		{18, 17, 0},		// 16	000011
		{-1, -1, -4},		// 17	0000111
		{-1, -1, 4},		// 18	0000110
		{23, 20, 0},		// 19	000010
		{22, 21, 0},		// 20	0000101
		{-1, -1, -5},		// 21	00001011
		{-1, -1, 5},		// 22	00001010
		{25, 24, 0},		// 23	0000100
		{-1, -1, -6},		// 24	00001001
		{-1, -1, 6},		// 25	00001000
		{50, 27, 0},		// 26	00000
		{31, 28, 0},		// 27	000001
		{30, 29, 0},		// 28	0000011
		{-1, -1, -7},		// 29	00000111
		{-1, -1, 7},		// 30	00000110
		{39, 32, 0},		// 31	0000010
		{36, 33, 0},		// 32	00000101
		{35, 34, 0},		// 33	000001011
		{-1, -1, -8},		// 34	0000010111
		{-1, -1, 8},		// 35	0000010110
		{38, 37, 0},		// 36	000001010
		{-1, -1, -9},		// 37	0000010101
		{-1, -1, 9},		// 38	0000010100
		{43, 40, 0},		// 39	00000100
		{42, 41, 0},		// 40	000001001
		{-1, -1, -10},		// 41	0000010011
		{-1, -1, 10},		// 42	0000010010
		{47, 44, 0},		// 43	000001000
		{46, 45, 0},		// 44	0000010001
		{-1, -1, -11},		// 45	00000100011
		{-1, -1, 11},		// 46	00000100010
		{49, 48, 0},		// 47	0000010000
		{-1, -1, -12},		// 48	00000100001
		{-1, -1, 12},		// 49	00000100000
		{82, 51, 0},		// 50	000000
		{67, 52, 0},		// 51	0000001
		{60, 53, 0},		// 52	00000011
		{57, 54, 0},		// 53	000000111
		{56, 55, 0},		// 54	0000001111
		{-1, -1, -13},		// 55	00000011111
		{-1, -1, 13},		// 56	00000011110
		{59, 58, 0},		// 57	0000001110
		{-1, -1, -14},		// 58	00000011101
		{-1, -1, 14},		// 59	00000011100
		{64, 61, 0},		// 60	000000110
		{63, 62, 0},		// 61	0000001101
		{-1, -1, -15},		// 62	00000011011
		{-1, -1, 15},		// 63	00000011010
		{66, 65, 0},		// 64	0000001100
		{-1, -1, -16},		// 65	00000011001
		{-1, -1, 16},		// 66	00000011000
		{75, 68, 0},		// 67	00000010
		{72, 69, 0},		// 68	000000101
		{71, 70, 0},		// 69	0000001011
		{-1, -1, -17},		// 70	00000010111
		{-1, -1, 17},		// 71	00000010110
		{74, 73, 0},		// 72	0000001010
		{-1, -1, -18},		// 73	00000010101
		{-1, -1, 18},		// 74	00000010100
		{79, 76, 0},		// 75	000000100
		{78, 77, 0},		// 76	0000001001
		{-1, -1, -19},		// 77	00000010011
		{-1, -1, 19},		// 78	00000010010
		{81, 80, 0},		// 79	0000001000
		{-1, -1, -20},		// 80	00000010001
		{-1, -1, 20},		// 81	00000010000
		{98, 83, 0},		// 82	0000000
		{91, 84, 0},		// 83	00000001
		{88, 85, 0},		// 84	000000011
		{87, 86, 0},		// 85	0000000111
		{-1, -1, -21},		// 86	00000001111
		{-1, -1, 21},		// 87	00000001110
		{90, 89, 0},		// 88	0000000110
		{-1, -1, -22},		// 89	00000001101
		{-1, -1, 22},		// 90	00000001100
		{95, 92, 0},		// 91	000000010
		{94, 93, 0},		// 92	0000000101
		{-1, -1, -23},		// 93	00000001011
		{-1, -1, 23},		// 94	00000001010
		{97, 96, 0},		// 95	0000000100
		{-1, -1, -24},		// 96	00000001001
		{-1, -1, 24},		// 97	00000001000
		{114, 99, 0},		// 98	00000000
		{107, 100, 0},		// 99	000000001
		{104, 101, 0},		// 100	0000000011
		{103, 102, 0},		// 101	00000000111
		{-1, -1, -25},		// 102	000000001111
		{-1, -1, 25},		// 103	000000001110
		{106, 105, 0},		// 104	00000000110
		{-1, -1, -26},		// 105	000000001101
		{-1, -1, 26},		// 106	000000001100
		{111, 108, 0},		// 107	0000000010
		{110, 109, 0},		// 108	00000000101
		{-1, -1, -27},		// 109	000000001011
		{-1, -1, 27},		// 110	000000001010
		{113, 112, 0},		// 111	00000000100
		{-1, -1, -28},		// 112	000000001001
		{-1, -1, 28},		// 113	000000001000
		{122, 115, 0},		// 114	000000000
		{119, 116, 0},		// 115	0000000001
		{118, 117, 0},		// 116	00000000011
		{-1, -1, -29},		// 117	000000000111
		{-1, -1, 29},		// 118	000000000110
		{121, 120, 0},		// 119	00000000010
		{-1, -1, -30},		// 120	000000000101
		{-1, -1, 30},		// 121	000000000100
		{-1, 123, 0},		// 122	0000000000
		{127, 124, 0},		// 123	00000000001
		{126, 125, 0},		// 124	000000000011
		{-1, -1, -31},		// 125	0000000000111
		{-1, -1, 31},		// 126	0000000000110
		{129, 128, 0},		// 127	000000000010
		{-1, -1, -32},		// 128	0000000000101
		{-1, -1, 32}		// 129	0000000000100
	};	

//	/** The length of max bitstring for the VLC table for TCOEFF. */
//	public final static int TCOEF_TAB_LENGTH = 12;

	/** The ESCAPE code for TCOEFF. */
	public final static int TCOEF_ESCAPE = 2;

	/** The VLC table for intra TCOEFF. */
	public final static int[][] INTRA_TCOEF_TAB = {
		{8, 1, 0},				// 0	
		{7, 2, 0},				// 1	1
		{6, 3, 0},				// 2	11
		{5, 4, 0},				// 3	111
		{-1, -1, 0, 0, 3},		// 4	1111
		{-1, -1, 0, 1, 1},		// 5	1110
		{-1, -1, 0, 0, 2},		// 6	110
		{-1, -1, 0, 0, 1},		// 7	10
		{28, 9, 0},				// 8	0
		{15, 10, 0},			// 9	01
		{12, 11, 0},			// 10	011
		{-1, -1, 1, 0, 1},		// 11	0111
		{14, 13, 0},			// 12	0110
		{-1, -1, 0, 0, 4},		// 13	01101
		{-1, -1, 0, 0, 5},		// 14	01100
		{21, 16, 0},			// 15	010
		{18, 17, 0},			// 16	0101
		{-1, -1, 0, 2, 1},		// 17	01011
		{20, 19, 0},			// 18	01010
		{-1, -1, 0, 0, 6},		// 19	010101
		{-1, -1, 0, 1, 2},		// 20	010100
		{25, 22, 0},			// 21	0100
		{24, 23, 0},			// 22	01001
		{-1, -1, 0, 0, 7},		// 23	010011
		{-1, -1, 0, 0, 8},		// 24	010010
		{27, 26, 0},			// 25	01000
		{-1, -1, 0, 3, 1},		// 26	010001
		{-1, -1, 0, 4, 1},		// 27	010000
		{52, 29, 0},			// 28	00
		{37, 30, 0},			// 29	001
		{34, 31, 0},			// 30	0011
		{33, 32, 0},			// 31	00111
		{-1, -1, 1, 1, 1},		// 32	001111
		{-1, -1, 1, 2, 1},		// 33	001110
		{36, 35, 0},			// 34	00110
		{-1, -1, 0, 5, 1},		// 35	001101
		{-1, -1, 1, 0, 2},		// 36	001100
		{45, 38, 0},			// 37	0010
		{42, 39, 0},			// 38	00101
		{41, 40, 0},			// 39	001011
		{-1, -1, 0, 0, 9},		// 40	0010111
		{-1, -1, 0, 1, 3},		// 41	0010110
		{44, 43, 0},			// 42	001010
		{-1, -1, 0, 2, 2},		// 43	0010101
		{-1, -1, 0, 7, 1},		// 44	0010100
		{49, 46, 0},			// 45	00100
		{48, 47, 0},			// 46	001001
		{-1, -1, 1, 5, 1},		// 47	0010011
		{-1, -1, 0, 6, 1},		// 48	0010010
		{51, 50, 0},			// 49	001000
		{-1, -1, 1, 3, 1},		// 50	0010001
		{-1, -1, 1, 4, 1},		// 51	0010000
		{90, 53, 0},			// 52	000
		{69, 54, 0},			// 53	0001
		{62, 55, 0},			// 54	00011
		{59, 56, 0},			// 55	000111
		{58, 57, 0},			// 56	0001111
		{-1, -1, 0, 0, 10},		// 57	00011111
		{-1, -1, 0, 0, 11},		// 58	00011110
		{61, 60, 0},			// 59	0001110
		{-1, -1, 0, 0, 12},		// 60	00011101
		{-1, -1, 0, 1, 4},		// 61	00011100
		{66, 63, 0},			// 62	000110
		{65, 64, 0},			// 63	0001101
		{-1, -1, 0, 3, 2},		// 64	00011011
		{-1, -1, 1, 9, 1},		// 65	00011010
		{68, 67, 0},			// 66	0001100
		{-1, -1, 0, 8, 1},		// 67	00011001
		{-1, -1, 0, 9, 1},		// 68	00011000
		{77, 70, 0},			// 69	00010
		{74, 71, 0},			// 70	000101
		{73, 72, 0},			// 71	0001011
		{-1, -1, 0, 10, 1},		// 72	00010111
		{-1, -1, 1, 0, 3},		// 73	00010110
		{76, 75, 0},			// 74	0001010
		{-1, -1, 1, 6, 1},		// 75	00010101
		{-1, -1, 1, 7, 1},		// 76	00010100
		{83, 78, 0},			// 77	000100
		{80, 79, 0},			// 78	0001001
		{-1, -1, 1, 8, 1},		// 79	00010011
		{82, 81, 0},			// 80	00010010
		{-1, -1, 0, 0, 13},		// 81	000100101
		{-1, -1, 0, 0, 14},		// 82	000100100
		{87, 84, 0},			// 83	0001000
		{86, 85, 0},			// 84	00010001
		{-1, -1, 0, 0, 15},		// 85	000100011
		{-1, -1, 0, 4, 2},		// 86	000100010
		{89, 88, 0},			// 87	00010000
		{-1, -1, 0, 0, 16},		// 88	000100001
		{-1, -1, 0, 1, 5},		// 89	000100000
		{124, 91, 0},			// 90	0000
		{107, 92, 0},			// 91	00001
		{100, 93, 0},			// 92	000011
		{97, 94, 0},			// 93	0000111
		{96, 95, 0},			// 94	00001111
		{-1, -1, 0, 1, 6},		// 95	000011111
		{-1, -1, 0, 2, 3},		// 96	000011110
		{99, 98, 0},			// 97	00001110
		{-1, -1, 0, 3, 3},		// 98	000011101
		{-1, -1, 0, 5, 2},		// 99	000011100
		{104, 101, 0},			// 100	0000110
		{103, 102, 0},			// 101	00001101
		{-1, -1, 0, 6, 2},		// 102	000011011
		{-1, -1, 0, 7, 2},		// 103	000011010
		{106, 105, 0},			// 104	00001100
		{-1, -1, 0, 11, 1},		// 105	000011001
		{-1, -1, 0, 12, 1},		// 106	000011000
		{115, 108, 0},			// 107	000010
		{112, 109, 0},			// 108	0000101
		{111, 110, 0},			// 109	00001011
		{-1, -1, 1, 0, 4},		// 110	000010111
		{-1, -1, 1, 1, 2},		// 111	000010110
		{114, 113, 0},			// 112	00001010
		{-1, -1, 1, 10, 1},		// 113	000010101
		{-1, -1, 1, 11, 1},		// 114	000010100
		{119, 116, 0},			// 115	0000100
		{118, 117, 0},			// 116	00001001
		{-1, -1, 1, 12, 1},		// 117	000010011
		{-1, -1, 1, 13, 1},		// 118	000010010
		{121, 120, 0},			// 119	00001000
		{-1, -1, 1, 14, 1},		// 120	000010001
		{123, 122, 0},			// 121	000010000
		{-1, -1, 0, 0, 17},		// 122	0000100001
		{-1, -1, 0, 0, 18},		// 123	0000100000
		{174, 125, 0},			// 124	00000
		{127, 126, 0},			// 125	000001
		{-1, -1, 2, 0, 0},		// 126	0000011
		{159, 128, 0},			// 127	0000010
		{144, 129, 0},			// 128	00000101
		{137, 130, 0},			// 129	000001011
		{134, 131, 0},			// 130	0000010111
		{133, 132, 0},			// 131	00000101111
		{-1, -1, 1, 20, 1},		// 132	000001011111
		{-1, -1, 1, 19, 1},		// 133	000001011110
		{136, 135, 0},			// 134	00000101110
		{-1, -1, 1, 18, 1},		// 135	000001011101
		{-1, -1, 1, 17, 1},		// 136	000001011100
		{141, 138, 0},			// 137	0000010110
		{140, 139, 0},			// 138	00000101101
		{-1, -1, 1, 6, 2},		// 139	000001011011
		{-1, -1, 1, 5, 2},		// 140	000001011010
		{143, 142, 0},			// 141	00000101100
		{-1, -1, 1, 0, 8},		// 142	000001011001
		{-1, -1, 0, 14, 1},		// 143	000001011000
		{152, 145, 0},			// 144	000001010
		{149, 146, 0},			// 145	0000010101
		{148, 147, 0},			// 146	00000101011
		{-1, -1, 0, 7, 3},		// 147	000001010111
		{-1, -1, 0, 2, 5},		// 148	000001010110
		{151, 150, 0},			// 149	00000101010
		{-1, -1, 0, 1, 10},		// 150	000001010101
		{-1, -1, 0, 6, 3},		// 151	000001010100
		{156, 153, 0},			// 152	0000010100
		{155, 154, 0},			// 153	00000101001
		{-1, -1, 0, 1, 9},		// 154	000001010011
		{-1, -1, 0, 0, 27},		// 155	000001010010
		{158, 157, 0},			// 156	00000101000
		{-1, -1, 0, 0, 26},		// 157	000001010001
		{-1, -1, 0, 0, 25},		// 158	000001010000
		{167, 160, 0},			// 159	00000100
		{164, 161, 0},			// 160	000001001
		{163, 162, 0},			// 161	0000010011
		{-1, -1, 1, 16, 1},		// 162	00000100111
		{-1, -1, 1, 15, 1},		// 163	00000100110
		{166, 165, 0},			// 164	0000010010
		{-1, -1, 1, 4, 2},		// 165	00000100101
		{-1, -1, 1, 3, 2},		// 166	00000100100
		{171, 168, 0},			// 167	000001000
		{170, 169, 0},			// 168	0000010001
		{-1, -1, 0, 9, 2},		// 169	00000100011
		{-1, -1, 0, 1, 8},		// 170	00000100010
		{173, 172, 0},			// 171	0000010000
		{-1, -1, 0, 0, 24},		// 172	00000100001
		{-1, -1, 0, 0, 23},		// 173	00000100000
		{190, 175, 0},			// 174	000000
		{183, 176, 0},			// 175	0000001
		{180, 177, 0},			// 176	00000011
		{179, 178, 0},			// 177	000000111
		{-1, -1, 0, 0, 19},		// 178	0000001111
		{-1, -1, 0, 0, 20},		// 179	0000001110
		{182, 181, 0},			// 180	000000110
		{-1, -1, 0, 1, 7},		// 181	0000001101
		{-1, -1, 0, 2, 4},		// 182	0000001100
		{187, 184, 0},			// 183	00000010
		{186, 185, 0},			// 184	000000101
		{-1, -1, 0, 3, 4},		// 185	0000001011
		{-1, -1, 0, 4, 3},		// 186	0000001010
		{189, 188, 0},			// 187	000000100
		{-1, -1, 0, 8, 2},		// 188	0000001001
		{-1, -1, 0, 5, 3},		// 189	0000001000
		{198, 191, 0},			// 190	0000000
		{195, 192, 0},			// 191	00000001
		{194, 193, 0},			// 192	000000011
		{-1, -1, 0, 13, 1},		// 193	0000000111
		{-1, -1, 1, 0, 5},		// 194	0000000110
		{197, 196, 0},			// 195	000000010
		{-1, -1, 1, 1, 3},		// 196	0000000101
		{-1, -1, 1, 2, 2},		// 197	0000000100
		{-1, 199, 0},			// 198	00000000
		{203, 200, 0},			// 199	000000001
		{202, 201, 0},			// 200	0000000011
		{-1, -1, 0, 0, 21},		// 201	00000000111
		{-1, -1, 0, 0, 22},		// 202	00000000110
		{205, 204, 0},			// 203	0000000010
		{-1, -1, 1, 0, 6},		// 204	00000000101
		{-1, -1, 1, 0, 7},		// 205	00000000100
	};
	

	/** The VLC table for inter TCOEFF. */
	public final static int[][] INTER_TCOEF_TAB = {
		{8, 1, 0},				// 0	
		{7, 2, 0},				// 1	1
		{6, 3, 0},				// 2	11
		{5, 4, 0},				// 3	111
		{-1, -1, 0, 0, 2},		// 4	1111
		{-1, -1, 0, 2, 1},		// 5	1110
		{-1, -1, 0, 1, 1},		// 6	110
		{-1, -1, 0, 0, 1},		// 7	10
		{28, 9, 0},				// 8	0
		{15, 10, 0},			// 9	01
		{12, 11, 0},			// 10	011
		{-1, -1, 1, 0, 1},		// 11	0111
		{14, 13, 0},			// 12	0110
		{-1, -1, 0, 3, 1},		// 13	01101
		{-1, -1, 0, 4, 1},		// 14	01100
		{21, 16, 0},			// 15	010
		{18, 17, 0},			// 16	0101
		{-1, -1, 0, 5, 1},		// 17	01011
		{20, 19, 0},			// 18	01010
		{-1, -1, 0, 0, 3},		// 19	010101
		{-1, -1, 0, 1, 2},		// 20	010100
		{25, 22, 0},			// 21	0100
		{24, 23, 0},			// 22	01001
		{-1, -1, 0, 6, 1},		// 23	010011
		{-1, -1, 0, 7, 1},		// 24	010010
		{27, 26, 0},			// 25	01000
		{-1, -1, 0, 8, 1},		// 26	010001
		{-1, -1, 0, 9, 1},		// 27	010000
		{52, 29, 0},			// 28	00
		{37, 30, 0},			// 29	001
		{34, 31, 0},			// 30	0011
		{33, 32, 0},			// 31	00111
		{-1, -1, 1, 1, 1},		// 32	001111
		{-1, -1, 1, 2, 1},		// 33	001110
		{36, 35, 0},			// 34	00110
		{-1, -1, 1, 3, 1},		// 35	001101
		{-1, -1, 1, 4, 1},		// 36	001100
		{45, 38, 0},			// 37	0010
		{42, 39, 0},			// 38	00101
		{41, 40, 0},			// 39	001011
		{-1, -1, 0, 0, 4},		// 40	0010111
		{-1, -1, 0, 10, 1},		// 41	0010110
		{44, 43, 0},			// 42	001010
		{-1, -1, 0, 11, 1},		// 43	0010101
		{-1, -1, 0, 12, 1},		// 44	0010100
		{49, 46, 0},			// 45	00100
		{48, 47, 0},			// 46	001001
		{-1, -1, 1, 5, 1},		// 47	0010011
		{-1, -1, 1, 6, 1},		// 48	0010010
		{51, 50, 0},			// 49	001000
		{-1, -1, 1, 7, 1},		// 50	0010001
		{-1, -1, 1, 8, 1},		// 51	0010000
		{90, 53, 0},			// 52	000
		{69, 54, 0},			// 53	0001
		{62, 55, 0},			// 54	00011
		{59, 56, 0},			// 55	000111
		{58, 57, 0},			// 56	0001111
		{-1, -1, 0, 0, 5},		// 57	00011111
		{-1, -1, 0, 1, 3},		// 58	00011110
		{61, 60, 0},			// 59	0001110
		{-1, -1, 0, 2, 2},		// 60	00011101
		{-1, -1, 0, 13, 1},		// 61	00011100
		{66, 63, 0},			// 62	000110
		{65, 64, 0},			// 63	0001101
		{-1, -1, 0, 14, 1},		// 64	00011011
		{-1, -1, 1, 9, 1},		// 65	00011010
		{68, 67, 0},			// 66	0001100
		{-1, -1, 1, 10, 1},		// 67	00011001
		{-1, -1, 1, 11, 1},		// 68	00011000
		{77, 70, 0},			// 69	00010
		{74, 71, 0},			// 70	000101
		{73, 72, 0},			// 71	0001011
		{-1, -1, 1, 12, 1},		// 72	00010111
		{-1, -1, 1, 13, 1},		// 73	00010110
		{76, 75, 0},			// 74	0001010
		{-1, -1, 1, 14, 1},		// 75	00010101
		{-1, -1, 1, 15, 1},		// 76	00010100
		{83, 78, 0},			// 77	000100
		{80, 79, 0},			// 78	0001001
		{-1, -1, 1, 16, 1},		// 79	00010011
		{82, 81, 0},			// 80	00010010
		{-1, -1, 0, 0, 6},		// 81	000100101
		{-1, -1, 0, 0, 7},		// 82	000100100
		{87, 84, 0},			// 83	0001000
		{86, 85, 0},			// 84	00010001
		{-1, -1, 0, 3, 2},		// 85	000100011
		{-1, -1, 0, 4, 2},		// 86	000100010
		{89, 88, 0},			// 87	00010000
		{-1, -1, 0, 15, 1},		// 88	000100001
		{-1, -1, 0, 16, 1},		// 89	000100000
		{124, 91, 0},			// 90	0000
		{107, 92, 0},			// 91	00001
		{100, 93, 0},			// 92	000011
		{97, 94, 0},			// 93	0000111
		{96, 95, 0},			// 94	00001111
		{-1, -1, 0, 17, 1},		// 95	000011111
		{-1, -1, 0, 18, 1},		// 96	000011110
		{99, 98, 0},			// 97	00001110
		{-1, -1, 0, 19, 1},		// 98	000011101
		{-1, -1, 0, 20, 1},		// 99	000011100
		{104, 101, 0},			// 100	0000110
		{103, 102, 0},			// 101	00001101
		{-1, -1, 0, 21, 1},		// 102	000011011
		{-1, -1, 0, 22, 1},		// 103	000011010
		{106, 105, 0},			// 104	00001100
		{-1, -1, 1, 0, 2},		// 105	000011001
		{-1, -1, 1, 17, 1},		// 106	000011000
		{115, 108, 0},			// 107	000010
		{112, 109, 0},			// 108	0000101
		{111, 110, 0},			// 109	00001011
		{-1, -1, 1, 18, 1},		// 110	000010111
		{-1, -1, 1, 19, 1},		// 111	000010110
		{114, 113, 0},			// 112	00001010
		{-1, -1, 1, 20, 1},		// 113	000010101
		{-1, -1, 1, 21, 1},		// 114	000010100
		{119, 116, 0},			// 115	0000100
		{118, 117, 0},			// 116	00001001
		{-1, -1, 1, 22, 1},		// 117	000010011
		{-1, -1, 1, 23, 1},		// 118	000010010
		{121, 120, 0},			// 119	00001000
		{-1, -1, 1, 24, 1},		// 120	000010001
		{123, 122, 0},			// 121	000010000
		{-1, -1, 0, 0, 8},		// 122	0000100001
		{-1, -1, 0, 0, 9},		// 123	0000100000
		{174, 125, 0},			// 124	00000
		{127, 126, 0},			// 125	000001
		{-1, -1, 2, 0, 0},		// 126	0000011
		{159, 128, 0},			// 127	0000010
		{144, 129, 0},			// 128	00000101
		{137, 130, 0},			// 129	000001011
		{134, 131, 0},			// 130	0000010111
		{133, 132, 0},			// 131	00000101111
		{-1, -1, 1, 40, 1},		// 132	000001011111
		{-1, -1, 1, 39, 1},		// 133	000001011110
		{136, 135, 0},			// 134	00000101110
		{-1, -1, 1, 38, 1},		// 135	000001011101
		{-1, -1, 1, 37, 1},		// 136	000001011100
		{141, 138, 0},			// 137	0000010110
		{140, 139, 0},			// 138	00000101101
		{-1, -1, 1, 36, 1},		// 139	000001011011
		{-1, -1, 1, 35, 1},		// 140	000001011010
		{143, 142, 0},			// 141	00000101100
		{-1, -1, 1, 34, 1},		// 142	000001011001
		{-1, -1, 1, 33, 1},		// 143	000001011000
		{152, 145, 0},			// 144	000001010
		{149, 146, 0},			// 145	0000010101
		{148, 147, 0},			// 146	00000101011
		{-1, -1, 0, 26, 1},		// 147	000001010111
		{-1, -1, 0, 25, 1},		// 148	000001010110
		{151, 150, 0},			// 149	00000101010
		{-1, -1, 0, 10, 2},		// 150	000001010101
		{-1, -1, 0, 6, 3},		// 151	000001010100
		{156, 153, 0},			// 152	0000010100
		{155, 154, 0},			// 153	00000101001
		{-1, -1, 0, 5, 3},		// 154	000001010011
		{-1, -1, 0, 4, 3},		// 155	000001010010
		{158, 157, 0},			// 156	00000101000
		{-1, -1, 0, 2, 4},		// 157	000001010001
		{-1, -1, 0, 1, 6},		// 158	000001010000
		{167, 160, 0},			// 159	00000100
		{164, 161, 0},			// 160	000001001
		{163, 162, 0},			// 161	0000010011
		{-1, -1, 1, 32, 1},		// 162	00000100111
		{-1, -1, 1, 31, 1},		// 163	00000100110
		{166, 165, 0},			// 164	0000010010
		{-1, -1, 1, 30, 1},		// 165	00000100101
		{-1, -1, 1, 29, 1},		// 166	00000100100
		{171, 168, 0},			// 167	000001000
		{170, 169, 0},			// 168	0000010001
		{-1, -1, 0, 24, 1},		// 169	00000100011
		{-1, -1, 0, 23, 1},		// 170	00000100010
		{173, 172, 0},			// 171	0000010000
		{-1, -1, 0, 1, 5},		// 172	00000100001
		{-1, -1, 0, 0, 12},		// 173	00000100000
		{190, 175, 0},			// 174	000000
		{183, 176, 0},			// 175	0000001
		{180, 177, 0},			// 176	00000011
		{179, 178, 0},			// 177	000000111
		{-1, -1, 0, 1, 4},		// 178	0000001111
		{-1, -1, 0, 2, 3},		// 179	0000001110
		{182, 181, 0},			// 180	000000110
		{-1, -1, 0, 3, 3},		// 181	0000001101
		{-1, -1, 0, 5, 2},		// 182	0000001100
		{187, 184, 0},			// 183	00000010
		{186, 185, 0},			// 184	000000101
		{-1, -1, 0, 6, 2},		// 185	0000001011
		{-1, -1, 0, 7, 2},		// 186	0000001010
		{189, 188, 0},			// 187	000000100
		{-1, -1, 0, 8, 2},		// 188	0000001001
		{-1, -1, 0, 9, 2},		// 189	0000001000
		{198, 191, 0},			// 190	0000000
		{195, 192, 0},			// 191	00000001
		{194, 193, 0},			// 192	000000011
		{-1, -1, 1, 25, 1},		// 193	0000000111
		{-1, -1, 1, 26, 1},		// 194	0000000110
		{197, 196, 0},			// 195	000000010
		{-1, -1, 1, 27, 1},		// 196	0000000101
		{-1, -1, 1, 28, 1},		// 197	0000000100
		{-1, 199, 0},			// 198	00000000
		{203, 200, 0},			// 199	000000001
		{202, 201, 0},			// 200	0000000011
		{-1, -1, 0, 0, 10},		// 201	00000000111
		{-1, -1, 0, 0, 11},		// 202	00000000110
		{205, 204, 0},			// 203	0000000010
		{-1, -1, 1, 0, 3},		// 204	00000000101
		{-1, -1, 1, 1, 2},		// 205	00000000100
	};

	/** The VLC table for dct_dc_size_luminance. */
	public final static int[][] DCT_DC_SIZE_LUMINANCE_TAB = {
	/*	
		10            2
		11            1
		010           3
		011           0
		001           4
		0001          5
		0000 1        6
		0000 01       7 
		0000 001      8
		0000 0001     9
		0000 0000 1   10
		0000 0000 01  11
		0000 0000 001 12
	*/
		{4, 1, 0},  
		{2, 3, 0},		 // 1 - 1  
		{-1, -1, 2},     // 2 - 10  
		{-1, -1, 1},     // 3 - 11
		{8, 5, 0},       // 4 - 0  
		{6, 7, 0},		 // 5 - 01  
		{-1, -1, 3},     // 6 - 010  
		{-1, -1, 0},     // 7 - 011
		{10, 9, 0},      // 8 - 00  
		{-1, -1, 4},     // 9 - 001
		{12, 11, 0},     //10 - 000  
		{-1, -1, 5},     //11 - 0001
		{14, 13, 0},     //12 - 0000  
		{-1, -1, 6},     //13 - 0000 1
		{16, 15, 0},     //14 - 0000 0  
		{-1, -1, 7},     //15 - 0000 01
		{18, 17, 0},     //16 - 0000 00  
		{-1, -1, 8},     //17 - 0000 001
		{20, 19, 0},     //18 - 0000 000 
		{-1, -1, 9},     //19 - 0000 0001
		{22, 21, 0},     //20 - 0000 0000  
		{-1, -1, 10},    //21 - 0000 0000 1
		{24, 23, 0},     //22 - 0000 0000 0  
		{-1, -1, 11},    //23 - 0000 0000 01
		{-1, 25, 0},     //24 - 0000 0000 00  
		{-1, -1, 12}     //25 - 0000 0000 001
		
	};

	/** The VLC table for dct_dc_size_chrominance. */
	public final static int[][] DCT_DC_SIZE_CHROMINANCE_TAB = {
		/*	
			11             0
			10             1
			01             2
			001            3
			0001           4
			0000 1         5
			0000 01        6 
			0000 001       7
			0000 0001      8
			0000 0000 1    9
			0000 0000 01   10
			0000 0000 001  11
			0000 0000 0001 12
		*/	
		{4, 1, 0},  
		{2, 3, 0},		 // 1 - 1   
		{-1, -1, 1},     // 2 - 10  
		{-1, -1, 0},     // 3 - 11
		{6, 5, 0},       // 4 - 0  
		{-1, -1, 2},	 // 5 - 01  
		{8, 7, 0},       // 6 - 00  
		{-1, -1, 3},     // 7 - 001
		{10, 9, 0},      // 8 - 000  
		{-1, -1, 4},     // 9 - 0001
		{12, 11, 0},     //10 - 0000  
		{-1, -1, 5},     //11 - 0000 1
		{14, 13, 0},     //12 - 0000 0  
		{-1, -1, 6},     //13 - 0000 01
		{16, 15, 0},     //14 - 0000 00  
		{-1, -1, 7},     //15 - 0000 001
		{18, 17, 0},     //16 - 0000 000  
		{-1, -1, 8},     //17 - 0000 0001
		{20, 19, 0},     //18 - 0000 0000 
		{-1, -1, 9},     //19 - 0000 0000 1
		{22, 21, 0},     //20 - 0000 0000 0 
		{-1, -1, 10},    //21 - 0000 0000 01
		{24, 23, 0},     //22 - 0000 0000 00  
		{-1, -1, 11},    //23 - 0000 0000 001
		{-1, 25, 0},     //24 - 0000 0000 0000  
		{-1, -1, 12}     //25 - 0000 0000 0001
		
	};
			
	/** The stuffing value for the mcbpc VLC code. */
	public final static int MCBPC_STUFFING = 5;

	/** The VLC table for mcbpc (I_VOP frames). */
	public final static int[][] MCBPC_1_TAB = 
	{
		{2, 1, 0}, 
		{-1, -1, 3, 0}, // 1 - 1 
		{3, 4, 0}, 		// 2 - 0
		{8, 5, 0}, 		// 3 - 00
		{6, 7, 0}, 		// 4 - 01
		{-1, -1, 3, 1}, // 5 - 001
		{-1, -1, 3, 2}, // 6 - 010
		{-1, -1, 3, 3}, // 7 - 011
		{10, 9, 0}, 	// 8 - 000
		{-1, -1, 4, 0}, // 9 - 0001
		{11, 12, 0}, 	// 10 - 0000
		{16, 13, 0}, 	// 11 - 00000
		{14, 15, 0}, 	// 12 - 00001
		{-1, -1, 4, 1}, // 13 - 000001
		{-1, -1, 4, 2}, // 14 - 000010
		{-1, -1, 4, 3}, // 15 - 000011
		{17, -1, 0}, 	// 16 - 0000 00
		{18, -1, 0}, 	// 17 - 0000 000
		{-1, 19, 0}, 	// 18 - 0000 0000
		{-1, -1, 5, 0}  // 19 - 0000 00001
	};


	/** The VLC table for mcbpc (P_VOP frames). */
	public final static int[][] MCBPC_2_TAB = {
		{2, 1, 0},			// 0	
		{-1, -1, 0, 0},		// 1	1
		{6, 3, 0},			// 2	0
		{5, 4, 0},			// 3	01
		{-1, -1, 1, 0},		// 4	011
		{-1, -1, 2, 0},		// 5	010
		{10, 7, 0},			// 6	00
		{9, 8, 0},			// 7	001
		{-1, -1, 0, 1},		// 8	0011
		{-1, -1, 0, 2},		// 9	0010
		{16, 11, 0},		// 10	000
		{13, 12, 0},		// 11	0001
		{-1, -1, 3, 0},		// 12	00011
		{15, 14, 0},		// 13	00010
		{-1, -1, 0, 3},		// 14	000101
		{-1, -1, 4, 0},		// 15	000100
		{24, 17, 0},		// 16	0000
		{21, 18, 0},		// 17	00001
		{20, 19, 0},		// 18	000011
		{-1, -1, 1, 1},		// 19	0000111
		{-1, -1, 1, 2},		// 20	0000110
		{23, 22, 0},		// 21	000010
		{-1, -1, 2, 1},		// 22	0000101
		{-1, -1, 2, 2},		// 23	0000100
		{30, 25, 0},		// 24	00000
		{27, 26, 0},		// 25	000001
		{-1, -1, 3, 3},		// 26	0000011
		{29, 28, 0},		// 27	0000010
		{-1, -1, 2, 3},		// 28	00000101
		{-1, -1, 3, 1},		// 29	00000100
		{36, 31, 0},		// 30	000000
		{33, 32, 0},		// 31	0000001
		{-1, -1, 3, 2},		// 32	00000011
		{35, 34, 0},		// 33	00000010
		{-1, -1, 1, 3},		// 34	000000101
		{-1, -1, 4, 1},		// 35	000000100
		{40, 37, 0},		// 36	0000000
		{39, 38, 0},		// 37	00000001
		{-1, -1, 4, 2},		// 38	000000011
		{-1, -1, 4, 3},		// 39	000000010
		{-1, 41, 0},		// 40	00000000
		{-1, -1, 5, 0},		// 41	000000001
	};

	/** The VLC table for cbpy in the case of four non-transparent blocks. */
	public final static int[][] CBPY_4_TAB = {
		{10, 1, 0},			// 0	
		{3, 2, 0},			// 1	1
		{-1, -1, 15, 0},	// 2	11
		{7, 4, 0},			// 3	10
		{6, 5, 0},			// 4	101
		{-1, -1, 7, 8},		// 5	1011
		{-1, -1, 11, 4},	// 6	1010
		{9, 8, 0},			// 7	100
		{-1, -1, 3, 12},	// 8	1001
		{-1, -1, 13, 2},	// 9	1000
		{18, 11, 0},		// 10	0
		{15, 12, 0},		// 11	01
		{14, 13, 0},		// 12	011
		{-1, -1, 5, 10},	// 13	0111
		{-1, -1, 14, 1},	// 14	0110
		{17, 16, 0},		// 15	010
		{-1, -1, 10, 5},	// 16	0101
		{-1, -1, 12, 3},	// 17	0100
		{24, 19, 0},		// 18	00
		{21, 20, 0},		// 19	001
		{-1, -1, 0, 15},	// 20	0011
		{23, 22, 0},		// 21	0010
		{-1, -1, 1, 14},	// 22	00101
		{-1, -1, 2, 13},	// 23	00100
		{28, 25, 0},		// 24	000
		{27, 26, 0},		// 25	0001
		{-1, -1, 4, 11},	// 26	00011
		{-1, -1, 8, 7},		// 27	00010
		{-1, 29, 0},		// 28	0000
		{31, 30, 0},		// 29	00001
		{-1, -1, 9, 6},		// 30	000011
		{-1, -1, 6, 9},		// 31	000010
	};

	/** The VLC table for cbpy in the case of three non-transparent blocks. */
	public final static int[][] CBPY_3_TAB = {
		/*
		   1      111 000  7 0
		   010    011 100  3 4
		   011    000 111  0 7
		   001    110 001  6 1
		   00010  100 011  4 3
		   00011  101 010  5 2
		   00001  010 101  2 5
		   000001 001 110  1 6
		*/
		{2, 1, 0},  
		{-1, -1, 7, 0},  // 1 - 1 
		{8, 3, 0},       // 2 - 0  
		{4,  5, 0},      // 3 - 01  
		{-1, -1, 3, 4},  // 6 - 010  
		{-1, -1, 0, 7},  // 7 - 011
		{10, 9, 0},      // 8 - 00  
		{-1, -1, 6, 1},  // 9 - 001 
		{14, 11, 0},     // 10 - 000  
		{12, 13, 0},     // 11 - 0001  
		{-1, -1, 4, 3},  // 12 - 00010  
		{-1, -1, 5, 2},  // 13 - 00011
		{16, 15, 0},     // 14 - 0000  
		{-1, -1, 2, 5},  // 15 - 00001 
		{-1, 17, 0},     // 16 - 00000  
		{-1, -1, 1, 6},  // 17 - 000001 
			
	};
	
	/** The VLC table for cbpy in the case of two non-transparent blocks. */
	public final static int[][] CBPY_2_TAB = {
		/*
		   1      11 00  3 0
		   01     10 01  2 1
		   001    01 10  1 2
		   0001   00 11  0 3
		*/
		{2, 1, 0},  
		{-1, -1, 3, 0},  // 1 - 1 
		{4, 3, 0},       // 2 - 0  
		{-1, -1, 2, 1},  // 3 - 01
		{6, 5, 0},       // 4 - 00  
		{-1, -1, 1, 2},  // 5 - 001
		{-1, 7, 0},      // 6 - 000  
		{-1, -1, 0, 3}   // 7 - 0001
	};

	/** The VLC table for cbpy in the case of one non-transparent blocks. */
	public final static int[][] CBPY_1_TAB = {
		/*
		   1      1 0  1 0
		   01     0 1  0 1
		*/
		{2, 1, 0},  
		{-1, -1, 1, 0},  // 1 - 1 
		{-1, 3, 0},      // 2 - 0  
		{-1, -1, 0, 1}   // 3 - 01
	};
	
	/** The input MPEG4 video bitstream. */
	private BitStream bitstream;

	/**
	 * Constructs an <code>Huffman</code> object using the specified bitstream object.
	 * @param bitstream the input video bitstream.
	 */	
	public Huffman(BitStream bitstream) {
		this.bitstream = bitstream;
	}
		
	/**
	 * Decodes the VLC code using the table specified by the <code>tab</code> parameter from the input bitstream. 
	 * @param max_length the maximum length of the input VLC code.
	 * @param tab the VLC (Huffman) table for the decoded value(s).
	 * @return the decoded value(s).
	 * @throws IOException raises if an error occurs.
	 */
	public int[] decode(int max_length, int tab[][]) throws IOException {
		int idx1 = 0, idx = 0;
		max_length++;
		int maske = (1 << max_length);
		long bits = bitstream.next_bits(max_length);

		while (idx != -1) {
			maske >>>= 1;
			idx1 = idx;
			idx = ((bits & maske) != 0) ? tab[idx][1] : tab[idx][0];
			max_length--;
		}
		bitstream.unget_bits(max_length + 1);
		return(tab[idx1]);
	}

}
