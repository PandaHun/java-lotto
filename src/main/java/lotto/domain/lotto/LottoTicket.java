package lotto.domain.lotto;

import java.util.Collections;
import java.util.List;
import lotto.domain.ResultGroup;
import lotto.domain.WinningResult;

public class LottoTicket {

    private static final String NOT_VALID_SIZE_EXCEPTION_MESSAGE = "[ERROR] 로또 티켓은 최소 1개 이상의 로또가 필요합니다.";

    private final List<Lotto> lottos;

    public LottoTicket(List<Lotto> lottos) {
        validateLottos(lottos);
        this.lottos = lottos;
    }

    public int lottoCount() {
        return lottos.size();
    }

    public List<Lotto> values() {
        return Collections.unmodifiableList(lottos);
    }

    public ResultGroup getResult(WinningLotto winningLotto) {
        ResultGroup resultGroup = new ResultGroup();
        lottos.stream()
            .map(lotto -> getWinningResult(winningLotto, lotto))
            .forEach(resultGroup::updateResult);
        return resultGroup;
    }

    private WinningResult getWinningResult(WinningLotto winningLotto, Lotto lotto) {
        int matchCount = winningLotto.howMatch(lotto);
        boolean matchBonus = winningLotto.matchBonus(lotto);
        return WinningResult.getResult(matchCount, matchBonus);
    }

    private void validateLottos(List<Lotto> lottos) {
        if (lottoSizeInvalid(lottos)) {
            throw new IllegalArgumentException(NOT_VALID_SIZE_EXCEPTION_MESSAGE);
        }
    }

    private boolean lottoSizeInvalid(List<Lotto> lottos) {
        return lottos == null || lottos.size() == 0;
    }
}
