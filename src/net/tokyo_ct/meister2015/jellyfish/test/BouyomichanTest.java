package net.tokyo_ct.meister2015.jellyfish.test;

import static org.junit.Assert.*;
import net.tokyo_ct.meister2015.jellyfish.bouyomichan.Bouyomichan;

import org.junit.Test;

public class BouyomichanTest {

	@Test
	public void test() {
		Bouyomichan b = new Bouyomichan();
		b.talk("アマで解説かじってる俺が来ましたよ。確かにガンでは中村の速球は140km/h台だけど、腕の振りが大きくて速く、初速から既にMAX近いスピードで 打者の前でも加速し続けてるから早い 距離が25mくらいあれば150後半は出てるよ彼");
		b.talk("まあ素人さんにわかりやすく言えば、140km/hのストレートって言っても2種類あって 打者の手元でやっと140km/hに到達するボールもあれば 初速から打者の手前まで一貫して140km/hのボールもあるってこと 勿論後者がの方が速く感じる ");

		try {
			Thread.sleep(3000); // 3000ミリ秒Sleepする
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		b.skip();
	}
}
