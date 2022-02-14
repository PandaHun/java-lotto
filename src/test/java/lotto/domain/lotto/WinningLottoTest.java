package lotto.domain.lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class WinningLottoTest {

    @Test
    void 우승_로또는_총_7개다() {
        List<LottoNumber> lottoNumbers = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6})
            .boxed()
            .map(LottoNumber::new)
            .collect(Collectors.toList());
        LottoNumber bonusBall = new LottoNumber(10);
        WinningLotto winningLotto = WinningLotto.of(lottoNumbers, bonusBall);
        assertThat(winningLotto.count()).isEqualTo(7);
        assertThat(winningLotto.bonusBall()).isEqualTo(new LottoNumber(10));
    }

    @Test
    void 우승_로또는_중복될_수_없다() {
        List<LottoNumber> lottoNumbers = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6})
            .boxed()
            .map(LottoNumber::new)
            .collect(Collectors.toList());
        LottoNumber bonusBall = new LottoNumber(1);
        assertThatThrownBy(() -> WinningLotto.of(lottoNumbers, bonusBall))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 우승_로또가_7개_외일_경우_예외가_발생_한다() {
        List<LottoNumber> underLottoNumbers = Arrays.stream(new int[]{1, 2, 3, 4, 5})
            .boxed()
            .map(LottoNumber::new)
            .collect(Collectors.toList());
        LottoNumber bonusBall = new LottoNumber(10);
        assertThatThrownBy(() -> WinningLotto.of(underLottoNumbers, bonusBall))
            .isInstanceOf(IllegalArgumentException.class);
        List<LottoNumber> overLottoNumbers = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6, 7})
            .boxed()
            .map(LottoNumber::new)
            .collect(Collectors.toList());
        assertThatThrownBy(() -> WinningLotto.of(overLottoNumbers, bonusBall))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 일치하는_로또_번호의_수를_반환한다() {
        // given
        List<LottoNumber> lottoNumbers = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6})
            .boxed()
            .map(LottoNumber::new)
            .collect(Collectors.toList());
        Lotto lotto = Lotto.from(lottoNumbers);
        List<LottoNumber> winningLottoNumbers = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6})
            .boxed()
            .map(LottoNumber::new)
            .collect(Collectors.toList());
        LottoNumber bonusBall = new LottoNumber(7);
        WinningLotto winningLotto = WinningLotto.of(winningLottoNumbers, bonusBall);
        // when
        int expectMatchCount = winningLotto.howMatch(lotto);
        // then
        assertThat(expectMatchCount).isEqualTo(6);
    }

    @Test
    void 보너스_볼의_일치_여부를_반환한다() {
        // given
        List<LottoNumber> lottoNumbers = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6})
            .boxed()
            .map(LottoNumber::new)
            .collect(Collectors.toList());
        Lotto lotto = Lotto.from(lottoNumbers);
        List<LottoNumber> winningLottoNumbers = Arrays.stream(new int[]{1, 2, 3, 4, 5, 6})
            .boxed()
            .map(LottoNumber::new)
            .collect(Collectors.toList());
        LottoNumber bonusBall = new LottoNumber(7);
        WinningLotto winningLotto = WinningLotto.of(winningLottoNumbers, bonusBall);
        // when
        boolean expectMatchBonus = winningLotto.matchBonus(lotto);
        // then
        assertThat(expectMatchBonus).isFalse();
    }
}
