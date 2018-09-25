// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: songzhif
// UT Student #: 1004359026
// Author: Zhifei Song
//
// Student2:
// UTORID user_name: xuxinzhe
// UT Student #: 1004050661
// Author: Xinzheng Xu
//
// Student3:
// UTORID user_name: wangq150
// UT Student #: 1004193419
// Author: Qingtian Wang
//
// Student4:
// UTORID user_name: wangz442
// UT Student #: 1004154960
// Author: Zijian Wang
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

package test;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import command.Curl;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;

/**
 * @author songzhif
 *
 */
// **this test really depends on the validity of the given URL**
// **this test really depends on the validity of the given URL**
// **this test really depends on the validity of the given URL**
class CurlTest {

  class MockControllableFile extends ControllableFile {

  }

  class MockFileSystem extends ManagementOfContainerKernel {
    Map<String, String> map;

    public MockFileSystem() {
      map = new HashMap<String, String>();
    }

    public MockControllableFile createFileUnderWD(String fileName,
        String content) {
      map.put(fileName, content);
      // System.out.println(map.get("dog"));
      return null;
    }

    public boolean checkFileExist(String fileName) {
      return map.containsKey(fileName);
    }

    public String getFileContent(String fileName) {
      return map.get(fileName);
    }
  }

  // MockFileSystem mock;
  private Curl curlCommand = new Curl();
  private MockFileSystem mock;

  @BeforeEach
  void setUp() throws Exception {
    mock = new MockFileSystem();
  }

  @Test
  void testExecuteWithTXTExtendsFileNameCorrectness1() {
    String inputURL = "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt";
    curlCommand.execute(mock, new Object[] {inputURL});
    // fail("Not yet implemented");
    boolean expectation = true;
    boolean actual = mock.checkFileExist("073.txt");
    assertEquals(expectation, actual);
  }

  @Test
  void testExecuteWithTXTExtendsFileNameCorrectness2() {
    String inputURL = "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt/";
    curlCommand.execute(mock, new Object[] {inputURL});
    // fail("Not yet implemented");
    boolean expectation = true;
    boolean actual = mock.checkFileExist("073.txt");
    assertEquals(expectation, actual);
  }

  @Test
  void testExecuteWithCOMExtendsFileNameCorrectness1() {
    String inputURL = "http://stackoverflow.com";
    curlCommand.execute(mock, new Object[] {inputURL});
    // fail("Not yet implemented");
    boolean expectation = true;
    boolean actual = mock.checkFileExist("stackoverflow.com");
    assertEquals(expectation, actual);
  }

  @Test
  void testExecuteWithCOMExtendsFileNameCorrectness2() {
    String inputURL = "http://stackoverflow.com/";
    curlCommand.execute(mock, new Object[] {inputURL});
    // fail("Not yet implemented");
    boolean expectation = true;
    boolean actual = mock.checkFileExist("stackoverflow.com");
    assertEquals(expectation, actual);
  }

  @Test
  void testExecuteWithHTMLExtendsFileNameCorrectness1() {
    String inputURL = "https://www.utsc.utoronto.ca/~bretscher/b20/news.html";
    curlCommand.execute(mock, new Object[] {inputURL});
    boolean expectation = true;
    boolean actual = mock.checkFileExist("news.html");
    assertEquals(expectation, actual);
  }

  @Test
  void testExecuteWithHTMLExtendsFileNameCorrectness2() {
    String inputURL = "https://www.utsc.utoronto.ca/~bretscher/b20/news.html/";
    curlCommand.execute(mock, new Object[] {inputURL});
    boolean expectation = true;
    boolean actual = mock.checkFileExist("news.html");
    assertEquals(expectation, actual);
  }


  @Test
  void testExecuteWithTXTExtendsContentCorrectness1() {
    String inputURL = "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt";
    curlCommand.execute(mock, new Object[] {inputURL});
    String expectation =
        "There was once a king who had an illness, and no one believed that he\n"
            + "would come out of it with his life.  He had three sons who were much\n"
            + "distressed about it, and went down into the palace-garden and wept.\n"
            + "There they met an old man who inquired as to the cause of their\n"
            + "grief.  They told him that their father was so ill that he would most\n"
            + "certainly die, for nothing seemed to cure him.  Then the old man\n"
            + "said, \"I know of one more remedy, and that is the water of life.  If\n"
            + "he drinks of it he will become well again, but it is hard to find.\"\n"
            + "The eldest said, \"I will manage to find it.\" And went to the sick\n"
            + "king, and begged to be allowed to go forth in search of the water of\n"
            + "life, for that alone could save him.  \"No,\" said the king, \"the\n"
            + "danger of it is too great.  I would rather die.\"\n" + "\n"
            + "But he begged so long that the king consented.  The prince thought in\n"
            + "his heart, \"If I bring the water, then I shall be best beloved of my\n"
            + "father, and shall inherit the kingdom.\" So he set out, and when he\n"
            + "had ridden forth a little distance, a dwarf stood there in the road\n"
            + "who called to him and said, \"Whither away so fast?\" \"Silly shrimp,\"\n"
            + "said the prince, very haughtily, \"it is nothing to do with you.\" And\n"
            + "rode on.  But the little dwarf had grown angry, and had wished an\n"
            + "evil wish.  Soon after this the prince entered a ravine, and the\n"
            + "further he rode the closer the mountains drew together, and at last\n"
            + "the road became so narrow that he could not advance a step further.\n"
            + "It was impossible either to turn his horse or to dismount from the\n"
            + "saddle, and he was shut in there as if in prison.  The sick king\n"
            + "waited long for him, but he came not.\n" + "\n"
            + "Then the second son said, \"father, let me go forth to seek the\n"
            + "water.\" And thought to himself, \"If my brother is dead, then the\n"
            + "kingdom will fall to me.\" At first the king would not allow him to go\n"
            + "either, but at last he yielded, so the prince set out on the same\n"
            + "road that his brother had taken, and he too met the dwarf, who\n"
            + "stopped him to ask whither he was going in such haste.  \"Little\n"
            + "shrimp,\" said the prince, \"that is nothing to do with you.\" And rode\n"
            + "on without giving him another look.  But the dwarf bewitched him, and\n"
            + "he, like the other, rode into a ravine, and could neither go forwards\n"
            + "nor backwards.  So fare haughty people.\n" + "\n"
            + "As the second son also remained away, the youngest begged to be\n"
            + "allowed to go forth to fetch the water, and at last the king was\n"
            + "obliged to let him go.  When he met the dwarf and the latter asked\n"
            + "him whither he was going in such haste, he stopped, gave him an\n"
            + "explanation, and said, \"I am seeking the water of life, for my father\n"
            + "is sick unto death.\"\n" + "\n"
            + "\"Do you know, then, where that is to be found?\"\n" + "\n"
            + "\"No,\" said the prince.\n" + "\n"
            + "\"As you have borne yourself as is seemly, and not haughtily like your\n"
            + "false brothers, I will give you the information and tell you how you\n"
            + "may obtain the water of life.  It springs from a fountain in the\n"
            + "courtyard of an enchanted castle, but you will not be able to make\n"
            + "your way to it, if I do not give you an iron wand and two small\n"
            + "loaves of bread.  Strike thrice with the wand on the iron door of the\n"
            + "castle and it will spring open, inside lie two lions with gaping\n"
            + "jaws, but if you throw a loaf to each of them, they will be quieted.\n"
            + "Then hasten to fetch some of the water of life before the clock\n"
            + "strikes twelve else the door will shut again, and you will be\n"
            + "imprisoned.\"\n" + "\n"
            + "The prince thanked him, took the wand and the bread, and set out on\n"
            + "his way.  When he arrived, everything was as the dwarf had said.  The\n"
            + "door sprang open at the third stroke of the wand, and when he had\n"
            + "appeased the lions with the bread, he entered the castle, and came to\n"
            + "a large and splendid hall, wherein sat some enchanted princes whose\n"
            + "rings he drew off their fingers.  A sword and a loaf of bread were\n"
            + "lying there, which he carried away.  After this, he entered a\n"
            + "chamber, in which was a beautiful maiden who rejoiced when she saw\n"
            + "him, kissed him, and told him that he had set her free, and should\n"
            + "have the whole of her kingdom, and that if he would return in a year\n"
            + "their wedding should be celebrated.  Likewise she told him where the\n"
            + "spring of the water of life was, and that he was to hasten and draw\n"
            + "some of it before the clock struck twelve.  Then he went onwards, and\n"
            + "at last entered a room where there was a beautiful newly-made bed,\n"
            + "and as he was very weary, he felt inclined to rest a little.  So he\n"
            + "lay down and fell asleep.\n" + "\n"
            + "When he awoke, it was striking a quarter to twelve.  He sprang up in\n"
            + "a fright, ran to the spring, drew some water in a cup which stood\n"
            + "near, and hastened away.  But just as he was passing through the iron\n"
            + "door, the clock struck twelve, and the door fell to with such\n"
            + "violence that it carried away a piece of his heel.\n" + "\n"
            + "He, however, rejoicing at having obtained the water of life, went\n"
            + "homewards, and again passed the dwarf.  When the latter saw the sword\n"
            + "and the loaf, he said, \"With these you have won great wealth, with\n"
            + "the sword you can slay whole armies, and the bread will never come to\n"
            + "an end.\" But the prince would not go home to his father without his\n"
            + "brothers, and said, \"Dear dwarf, can you not tell me where my two\n"
            + "brothers are?  They went out before I did in search of the water of\n"
            + "life, and have not returned.\"\n" + "\n"
            + "\"They are imprisoned between two mountains,\" said the dwarf. \"I have\n"
            + "condemned them to stay there, because they were so haughty.\" Then the\n"
            + "prince begged until the dwarf released them, but he warned him and\n"
            + "said, \"Beware of them, for they have bad hearts.\" When his brothers\n"
            + "came, he rejoiced, and told them how things had gone with him, that\n"
            + "he had found the water of life and had brought a cupful away with\n"
            + "him, and had rescued a beautiful princess, who was willing to wait a\n"
            + "year for him, and then their wedding was to be celebrated and he\n"
            + "would obtain a great kingdom.\n" + "\n"
            + "After that they rode on together, and chanced upon a land where war\n"
            + "and famine reigned, and the king already thought he must perish, for\n"
            + "the scarcity was so great.  Then the prince went to him and gave him\n"
            + "the loaf, wherewith he fed and satisfied the whole of his kingdom,\n"
            + "and then the prince gave him the sword also wherewith he slew the\n"
            + "hosts of his enemies, and could now live in rest and peace.  The\n"
            + "prince then took back his loaf and his sword, and the three brothers\n"
            + "rode on.  But after this they entered two more countries where war\n"
            + "and famine reigned and each time the prince gave his loaf and his\n"
            + "sword to the kings, and had now delivered three kingdoms, and after\n"
            + "that they went on board a ship and sailed over the sea.  During the\n"
            + "passage, the two eldest conversed apart and said, \"The youngest has\n"
            + "found the water of life and not we, for that our father will give him\n"
            + "the kingdom - the kingdom which belongs to us, and he will rob us of\n"
            + "all our fortune.\" They then began to seek revenge, and plotted with\n"
            + "each other to destroy him.  They waited until they found him fast\n"
            + "asleep, then they poured the water of life out of the cup, and took\n"
            + "it for themselves, but into the cup they poured salt sea-water.\n"
            + "\n"
            + "Now therefore, when they arrived home, the youngest took his cup to\n"
            + "the sick king in order that he might drink out of it, and be cured.\n"
            + "But scarcely had he drunk a very little of the salt sea-water than he\n"
            + "became still worse than before.  And as he was lamenting over this,\n"
            + "the two eldest brothers came, and accused the youngest of having\n"
            + "intended to poison him, and said that they had brought him the true\n"
            + "water of life, and handed it to him.  He had scarcely tasted it, when\n"
            + "he felt his sickness departing, and became strong and healthy as in\n"
            + "the days of his youth.\n" + "\n"
            + "After that they both went to the youngest, mocked him, and said, \"You\n"
            + "certainly found the water of life, but you have had the pain, and we\n"
            + "the gain, you should have been cleverer, and should have kept your\n"
            + "eyes open.  We took it from you whilst you were asleep at sea, and\n"
            + "when a year is over, one of us will go and fetch the beautiful\n"
            + "princess. But beware that you do not disclose aught of this to our\n"
            + "father, indeed he does not trust you, and if you say a single word,\n"
            + "you shall lose your life into the bargain, but if you keep silent,\n"
            + "you shall have it as a gift.\"\n" + "\n"
            + "The old king was angry with his youngest son, and thought he had\n"
            + "plotted against his life.  So he summoned the court together and had\n"
            + "sentence pronounced upon his son, that he should be secretly shot.\n"
            + "And once when the prince was riding forth to the chase, suspecting no\n"
            + "evil, the king's huntsman was told to go with him, and when they were\n"
            + "quite alone in the forest, the huntsman looked so sorrowful that the\n"
            + "prince said to him, \"Dear huntsman, what ails you?\" The huntsman\n"
            + "said, \"I cannot tell you, and yet I ought.\" Then the prince said,\n"
            + "\"Say openly what it is, I will pardon you.\" \"Alas,\" said the\n"
            + "huntsman, \"I am to shoot you dead, the king has ordered me to do it.\"\n"
            + "Then the prince was shocked, and said, \"Dear huntsman, let me live,\n"
            + "there, I give you my royal garments, give me your common ones in\n"
            + "their stead.\" The huntsman said, \"I will willingly do that, indeed I\n"
            + "would not have been able to shoot you.\" Then they exchanged clothes,\n"
            + "and the huntsman returned home, while the prince went further into\n"
            + "the forest.\n" + "\n"
            + "After a time three waggons of gold and precious stones came to the\n"
            + "king for his youngest son, which were sent by the three kings who had\n"
            + "slain their enemies with the prince's sword, and maintained their\n"
            + "people with his bread, and who wished to show their gratitude for it.\n"
            + "The old king then thought, \"Can my son have been innocent?\" And said\n"
            + "to his people, \"Would that he were still alive, how it grieves me\n"
            + "that I have suffered him to be killed.\" \"He still lives,\" said the\n"
            + "huntsman, \"I could not find it in my heart to carry out your\n"
            + "command.\" And told the king how it had happened.  Then a stone fell\n"
            + "from the king's heart, and he had it proclaimed in every country that\n"
            + "his son might return and be taken into favor again.\n" + "\n"
            + "The princess, however, had a road made up to her palace which was\n"
            + "quite bright and golden, and told her people that whosoever came\n"
            + "riding straight along it to her, would be the right one and was to be\n"
            + "admitted, and whoever rode by the side of it, was not the right one\n"
            + "and was not to be admitted.\n" + "\n"
            + "As the time was now close at hand, the eldest thought he would hasten\n"
            + "to go to the king's daughter, and give himself out as her rescuer,\n"
            + "and thus win her for his bride, and the kingdom to boot.  Therefore\n"
            + "he rode forth, and when he arrived in front of the palace, and saw\n"
            + "the splendid golden road, he thought, it would be a sin and a shame\n"
            + "if I were to ride over that.  And turned aside, and rode on the right\n"
            + "side of it. But when he came to the door, the servants told him that\n"
            + "he was not the right one, and was to go away again.\n" + "\n"
            + "Soon after this the second prince set out, and when he came to the\n"
            + "golden road, and his horse had put one foot on it, he thought, it\n"
            + "would be a sin and a shame, a piece might be trodden off.  And he\n"
            + "turned aside and rode on the left side of it, and when he reached the\n"
            + "door, the attendants told him he was not the right one, and he was to\n"
            + "go away again.\n" + "\n"
            + "When at last the year had entirely expired, the third son likewise\n"
            + "wished to ride out of the forest to his beloved, and with her forget\n"
            + "his sorrows.  So he set out and thought of her so incessantly, and\n"
            + "wished to be with her so much, that he never noticed the golden road\n"
            + "at all.  So his horse rode onwards up the middle of it, and when he\n"
            + "came to the door, it was opened and the princess received him with\n"
            + "joy, and said he was her saviour, and lord of the kingdom, and their\n"
            + "wedding was celebrated with great rejoicing.  When it was over she\n"
            + "told him that his father invited him to come to him, and had forgiven\n"
            + "him.\n" + "\n"
            + "So he rode thither, and told him everything, how his brothers had\n"
            + "betrayed him, and how he had nevertheless kept silence.  The old king\n"
            + "wished to punish them, but they had put to sea, and never came back\n"
            + "as long as they lived.\n";
    String actual = mock.getFileContent("073.txt");
    // System.out.println(expectation);
    // System.out.println("------------------------------------------------------------------");
    // System.out.println(actual);
    assertEquals(expectation, actual);
  }

  @Test
  void testExecuteWithHTMLExtendsContentCorrectness1() {
    String inputURL = "https://www.utsc.utoronto.ca/~bretscher/b20/news.html/";
    curlCommand.execute(mock, new Object[] {inputURL});
    String expectation =
        "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n"
            + "<html>\n" + "<head>\n"
            + "<title>CSCB20 -Introduction to Databases and Web Applications\n"
            + "</title>\n"
            + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n"
            + "<link href=\"stylesheet.css\" rel=\"stylesheet\" type=\"text/css\"> <script language=\"JavaScript\" type=\"text/JavaScript\">\n"
            + "</script>\n" + "</head>\n" + "\n" + "<body>\n" + "\n"
            + "<div class=\"heading\">Announcements</div>\n"
            + "<div class=\"leftnavigation\"> <br>\n" + "  <ul>\n"
            + "    <h3><a href=\"index.html\">Home&nbsp;</a></h3>\n"
            + "    <h3><a\n"
            + "href=\"calendar.html\">Calendar&nbsp;</a></h3>\n"
            + "    <h3><a href=\"news.html\">News&nbsp;</a></h3>\n" + "<h3><a\n"
            + "href=\"http://piazza.com/mail.utoronto.ca/winter2016/cscb20/home\">Discussion Board&nbsp;</a></h3>\n"
            + "\n"
            + "    <h3><a href=\"lectures.html\">Lectures&nbsp;</a></h3>\n"
            + "    <h3><a\n" + "href=\"labs.html\">Labs&nbsp;</a></h3>\n"
            + "    <h3><a\n"
            + "href=\"assignments.html\">Assignments&nbsp;</a></h3>\n"
            + "    <h3><a\n" + "href=\"tests.html\">Tests&nbsp;</a></h3>\n"
            + "    <h3><a\n"
            + "href=\"resources.html\">Resources&nbsp;</a></h3>\n" + "  </ul>\n"
            + "</div>\n" + "\n" + "\n" + "<div class=\"content\">\n"
            + "<!--<h2>Feb. 22</h2>\n"
            + "<p>Midterm is in tutorial - 50 min long and in week 8 (Feb. 29). The content will include:\n"
            + "<ul>\n" + "<li>Relational Diagrams (10% - 15%)</li>\n"
            + "<li>Relational Algebra (10-20%)</li>\n"
            + "<li>General Database short answer questions (5-15%)</li>\n"
            + "<li>MySQL query writing (40%-50%)</li>\n"
            + "<li>Other MySQL commands (10% - 15%)</li>\n"
            + "<li>E-R model - (0% - will be on the final, not on the midterm)</li>\n"
            + "</ul></p>-->\n" + "<h2>Jan. 2</h2>\n"
            + "<p>Welcome to CSCB20.  Please note that tutorials will begin in week 2.</p>\n"
            + "\n" + "\n" + "\n" + "\n" + "\n" + "</body>\n" + "</html>\n" + "";
    String actual = mock.getFileContent("news.html");
    // System.out.println(expectation);
    // System.out.println("------------------------------------------------------------------");
    // System.out.println(actual);
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckArgFormat1() {
    boolean expectation = true;
    boolean actual = curlCommand.checkArgFormat(new String[] {"0"});
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckArgFormat2() {
    boolean expectation = false;
    boolean actual = curlCommand.checkArgFormat(new String[] {"0", ""});
    assertEquals(expectation, actual);
  }

}
