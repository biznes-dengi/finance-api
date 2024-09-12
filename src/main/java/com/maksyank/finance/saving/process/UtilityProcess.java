package com.maksyank.finance.saving.process;

import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.user.service.AccountProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Async
@RequiredArgsConstructor
public class UtilityProcess {
    final private AccountProcess accountProcess;
    final private SavingDao savingDao;

    // TODO it's temporary impl, task in Notion
    // TODO there's bug with time zone, right now the impl only for one time zone
    @Async
    @Scheduled(cron = "0 0 * * * *")
    public void scheduledCheckSavingsIfOverdue() {
        this.accountProcess.getListIdsOfUsers().stream()
                .map(userId -> savingDao.fetchSavingsByStateAndDeadlineIsNotNull(SavingState.ACTIVE, userId))
                .filter(listSaving -> !listSaving.isEmpty())
                .forEach(listSaving ->
                        listSaving.stream()
                                .filter(saving -> saving.getDeadline().isBefore(LocalDate.now()) ||
                                        saving.getDeadline().isEqual(LocalDate.now()))
                                .forEach(saving -> {
                                    saving.setState(SavingState.OVERDUE);
                                    savingDao.createSaving(saving);
                                })
                );
    }
}
