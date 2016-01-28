package com.kankan.op.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import com.xunlei.util.Log;
import com.xunlei.util.StringTools;

public class GenUniqueCode {

    private int MAX_CHARS = 36;
    private char[] chars = {
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'I',
        'J',
        'K',
        'L',
        'M',
        'N',
        'O',
        'P',
        'Q',
        'R',
        'S',
        'T',
        'U',
        'V',
        'W',
        'X',
        'Y',
        'Z'
    };
    private int codeLength;

    private int[] validateArray = {
        89,
        11,
        79,
        37,
        41,
        11,
        2,
        13,
        63,
        61,
        17,
        53,
        97,
        2,
        101
    };
    private char[] charCode = {
        '0',
        'W',
        'S',
        '9',
        'P'
    };
    private char[] charCinema = {
        '3',
        'M',
        'K',
        'Z',
        'I'
    };
    private char[] charExperience = {
        '6',
        'Y',
        'R',
        '2',
        'F'
    };
    private char[] charVoucher = {
        '7',
        'N',
        'Q',
        'B',
        '1'
    };

    private static final Logger log = Log.getLogger();

    public GenUniqueCode(int codeLength) {
        this.codeLength = codeLength;
    }

    public List<String> genActivateCode(int codesNum, char fir, int ticketType) {
        List<String> list = new ArrayList<String>();

        char[] charTemp = charCode;
        switch (ticketType) {
        case 0:// 卡密
            charTemp = charCode;
            break;
        case 1:// 观影券
            charTemp = charCinema;
            break;
        case 2:// 体验会员
            charTemp = charExperience;
            break;
        case 3:// 代金券
            charTemp = charVoucher;
            break;
        default:// 暂无其他类型 直接返回null
            return list;
        }

        try {
            char[] charArray = new char[codeLength];
            Random rand = new Random();
            int leftNum = codesNum; // 需产生激活码的个数
            for (int i = 0; i < leftNum; i++) {
                charArray[0] = fir;
                int result = fir * validateArray[0];
                for (int j = 1; j < codeLength - 1; j++) {
                    if (j == 6) {
                        charArray[j] = charTemp[rand.nextInt(MAX_CHARS) % 5];
                    } else {
                        charArray[j] = chars[rand.nextInt(MAX_CHARS)];
                    }
                    int k = j;
                    if (j > 14) {
                        k = 14;
                    }
                    result += charArray[k] * validateArray[k];
                }
                charArray[codeLength - 1] = chars[(result >> 2) % MAX_CHARS];// 加一位验证位
                String ticketId = new String(charArray);
                if (isConfusable(ticketId)) {
                    i--;
                    // System.out.println(ticketId + "不符合要求");
                } else {
                    list.add(new String(charArray));
                }
            }
        } catch (Exception e) {
            log.error("gen code error !e:{}", e);
        }
        return list;
    }

    /**
     * 判断生成的激活码里面有没有易混淆的字符，防止出现激活码难以识别的问题
     */
    public boolean isConfusable(String ticketId) {
        if (StringTools.isEmpty(ticketId)) {
            return true;
        }
        for (int i = 0; i < ticketId.length(); i++) {
            char ch = ticketId.charAt(i);
            if (ch == 'O' || ch == 'o' || ch == '0' || ch == '1' || ch == 'l') {
                return true;
            }
        }
        return false;
    }

    public boolean checkTickets(String ticketId) {
        int result = 0;
        for (int i = 0; i < ticketId.length() - 1; i++) {
            int k = i;
            if (i > validateArray.length - 1) {
                k = 14;
            }
            result += ticketId.charAt(i) * validateArray[k];
        }
        return chars[((result >> 2) % 36)] == ticketId.charAt(ticketId.length() - 1);
    }

    public int getTicketType(String ticketId) {
        if (StringTools.isEmpty(ticketId) || ticketId.length() < 7) {
            return -1;
        }
        char flag = ticketId.charAt(6);
        for (char c : charCode) {
            if (c == flag) {
                return 0;
            }
        }
        for (char c : charCinema) {
            if (c == flag) {
                return 1;
            }
        }
        for (char c : charExperience) {
            if (c == flag) {
                return 2;
            }
        }
        for (char c : charVoucher) {
            if (c == flag) {
                return 3;
            }
        }
        return -1;
    }
}
