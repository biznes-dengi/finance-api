import {useNavigate} from 'react-router-dom';

import {APP_ICON, Button, ButtonConfig, Card, ListItem, Management, Transfer, useDrawer} from '@shared/ui';

import {goalModel} from '@entities/goal';
import {APP_PATH, APP_TEXT} from '@shared/config';
import {textHelpers} from '@shared/lib';

export function GoalManagement() {
	const navigate = useNavigate();

	const {rows, goalItem} = goalModel.useData();

	const {openDrawer, Drawer} = useDrawer();

	const buttonConfigs = [
		{
			name: APP_TEXT.create,
			icon: APP_ICON.createGoal,
			onClick: ({navigate}) => navigate(APP_PATH.createGoal),
		},
		{
			name: APP_TEXT.fund,
			icon: APP_ICON.fund,
		},
		{
			name: APP_TEXT.transfer,
			icon: APP_ICON.transfer,
			onClick: openDrawer,
		},
		{
			name: APP_TEXT.more,
			icon: APP_ICON.more,
			onClick: () => {},
		},
	] as ButtonConfig[];

	return (
		<>
			<Card>
				<Management item={goalItem} buttonConfigs={buttonConfigs} subtitle={APP_TEXT.accumulation} />

				<div className='flex justify-between px-4 py-3 text-sm'>
					<div className='text-primary-grey'>{APP_TEXT.items}</div>
					<div className='text-primary-violet'>{APP_TEXT.seeAll}</div>
				</div>

				{rows.map((row) => (
					<Button key={row.name} onClick={() => navigate(APP_PATH.goalDetails)}>
						<ListItem
							mask={<div role='mask' className='border-2 border-primary-violet bg-secondary-grey' />}
							name={row.name}
							description={textHelpers.getRatio(
								textHelpers.getAmountWithCurrency(row.savedAmount, row.currencySymbol),
								textHelpers.getAmountWithCurrency(row.targetAmount, row.currencySymbol),
							)}
						/>
					</Button>
				))}
			</Card>

			<Drawer>
				<Transfer />
			</Drawer>
		</>
	);
}
