package it.almaviva.roomtestapp.utils;

import java.util.Calendar;

import it.almaviva.roomtestapp.entity.Message;

/**
 * Created by m.spalletti on 03/01/2018.
 */

public class MessageUtil {
	private static String[] bodies = {
			"Phasellus ultricies nisl ac pharetra lacinia.",
			"Aliquam lacinia mauris vitae urna consequat, ac tincidunt ligula dapibus.",
			"Maecenas pulvinar erat mattis, elementum eros nec, vestibulum metus.",
			"Fusce accumsan libero at arcu luctus, nec pulvinar sem semper.",
			"Quisque accumsan lorem interdum congue interdum.",
			"Etiam maximus libero sed est bibendum feugiat.",
			"Quisque accumsan ligula eu consectetur pretium.",
			"Nam tincidunt dolor vel turpis pretium, vel aliquam arcu iaculis.",
			"Pellentesque gravida mauris sed dui luctus dapibus.",
			"Quisque ut diam interdum, mollis est at, volutpat ipsum.",
			"Donec placerat turpis mattis, semper ipsum nec, tincidunt diam.",
			"Aliquam consequat purus vitae est vestibulum suscipit a viverra nunc.",
			"Aenean sit amet nisl vestibulum, ornare erat ac, vulputate orci.",
			"Ut quis urna interdum, tincidunt libero vel, auctor sem.",
			"Integer in mauris sed nunc pretium sollicitudin.",
			"Praesent non nunc a justo sollicitudin pellentesque bibendum eu turpis.",
			"Integer elementum nisl sit amet sapien sollicitudin, at aliquet elit ullamcorper.",
			"Sed vitae dolor facilisis, auctor est sit amet, egestas quam.",
			"Etiam suscipit arcu ut odio rutrum fermentum.",
			"Duis sed nisi vitae neque vehicula accumsan non non orci.",
			"Donec scelerisque nisl vitae ipsum venenatis facilisis.",
			"Morbi ornare diam ac justo elementum, ac suscipit mauris gravida.",
			"Maecenas non dolor vel ex faucibus suscipit.",
			"Vestibulum ultrices nibh et dolor tincidunt blandit.",
			"Nam vel nisl vestibulum, semper ante eget, hendrerit est.",
			"Fusce congue velit at mauris dignissim dapibus.",
			"Aenean sed elit non arcu placerat pellentesque.",
			"Vestibulum ac velit a turpis congue feugiat.",
			"Vivamus ac nisl a ipsum scelerisque faucibus.",
			"Aliquam sollicitudin metus in eros imperdiet, at lacinia ex fringilla.",
			"Morbi aliquam massa id risus convallis vehicula.",
			"Vestibulum ac mi in dui auctor sollicitudin nec quis ante.",
			"In vitae arcu nec velit consectetur vestibulum.",
			"Sed rutrum enim a erat porttitor, ac tincidunt libero cursus.",
			"Mauris non ex ut ex ullamcorper malesuada eget vitae magna.",
			"Etiam nec massa in lectus sodales posuere vitae a enim.",
			"Donec euismod ante quis risus maximus gravida.",
			"Vestibulum mollis orci a tincidunt auctor.",
			"Proin ac lacus lacinia, rutrum nisi at, placerat nulla.",
			"Sed ut libero a eros pharetra imperdiet.",
			"Cras vitae eros porttitor, porttitor dolor ac, efficitur neque.",
			"Nullam tempus nisl in est accumsan aliquam.",
			"Sed vitae felis eu ipsum auctor molestie.",
			"Cras pharetra turpis id lacus bibendum iaculis.",
			"Duis rhoncus felis vitae pretium congue.",
			"Duis vel ex quis ante tempus ullamcorper malesuada ut arcu.",
			"Donec maximus nisl sed lectus aliquam feugiat.",
			"Suspendisse vitae nisi at justo hendrerit vulputate.",
			"Integer efficitur nisi vitae urna aliquet, sed volutpat nulla aliquet.",
			"Nulla a libero ac diam euismod fringilla quis luctus justo.",
			"Quisque eleifend turpis at nisi mollis aliquet nec ut lorem.",
			"Cras nec odio vitae nulla consequat feugiat vitae vitae diam.",
			"Quisque ac lacus quis nulla mattis scelerisque eleifend vitae libero.",
			"Nunc pretium dui ultricies vehicula congue.",
			"Curabitur pulvinar nulla at lectus mattis pretium.",
			"Donec eu magna venenatis, vulputate urna ut, viverra est.",
			"Suspendisse ullamcorper nisi eget ex tristique, sed consequat eros euismod.",
			"Nam ut neque mollis ipsum auctor tempus vitae sed dolor.",
			"Suspendisse pretium ex scelerisque ante elementum, vitae porta erat dignissim.",
			"Vivamus aliquam neque id nibh molestie, in feugiat libero dapibus.",
			"Donec accumsan sapien sed mauris tincidunt sagittis.",
			"Curabitur blandit leo non mi congue porta.",
			"Vivamus blandit augue et ex feugiat, non viverra nisl tempor.",
			"Proin et mauris quis nisi accumsan dignissim.",
			"Aenean dapibus leo condimentum elit pharetra tempus.",
			"Suspendisse pretium ante et placerat convallis.",
			"Nulla laoreet libero in accumsan eleifend.",
			"Sed tempus ipsum sit amet dui mattis, quis imperdiet leo tincidunt.",
			"Etiam vel lectus sit amet augue ultricies volutpat.",
			"Maecenas convallis justo eget convallis condimentum.",
			"Praesent ut nibh quis velit pharetra rhoncus.",
			"Donec suscipit lorem quis tristique tempor.",
			"Nam convallis nunc ac nisl hendrerit, vitae viverra justo sagittis.",
			"Nunc eu risus consequat, bibendum enim a, eleifend erat.",
			"Sed in turpis aliquam, accumsan sapien in, convallis tellus.",
			"Morbi in arcu ut tortor vestibulum cursus at id ex.",
			"Sed ac erat at enim semper posuere.",
			"Etiam mattis ipsum sed elementum volutpat.",
			"Aliquam eu nisi porta, faucibus leo efficitur, ultricies est.",
			"Morbi sagittis tellus ut porta pharetra.",
			"Pellentesque rutrum ante vitae quam finibus iaculis.",
			"Phasellus eget metus suscipit ex fermentum accumsan a eget ligula.",
			"Ut nec elit vel erat porttitor convallis a ac massa.",
			"Suspendisse efficitur eros vel metus consectetur, vitae lobortis neque scelerisque.",
			"Nunc sit amet mauris sed magna viverra rutrum in vitae nisi.",
			"Aenean eu enim ut ipsum dictum varius."
	};
	private static int next = 0;

	public static Message getRandom() {
		Message retVal = new Message();
		retVal.timeStamp = Calendar.getInstance().getTime();
		retVal.body = bodies[next];
		next = (next + 1) % bodies.length;
		return retVal;
	}
}
