import {Button, BUTTON_TYPE, ButtonConfig} from '@shared/ui';

import {cn, textHelpers} from '@shared/lib';
import {TotalGoal} from '@entities/goal';

type Props = {
	item: TotalGoal;
	buttonConfigs: ButtonConfig[];
	subtitle?: string;
};

export function Management(props: Props) {
	const {item, buttonConfigs, subtitle} = props;

	return (
		<div role='management' className='p-4'>
			<div className={cn('flex justify-between pb-4')}>
				<div>
					<div className='mb-1 text-3xl font-semibold'>
						{textHelpers.getAmountWithCurrency(item.amount, item.currencySymbol)}
					</div>
					{subtitle && <div className='text-sm font-light text-primary-grey'>{subtitle}</div>}
				</div>
				<div className='h-10 w-10 rounded-xl bg-secondary-grey' />
			</div>

			<div className='flex justify-around'>
				{buttonConfigs.map((buttonConfig) => (
					<Button
						key={buttonConfig.name}
						type={BUTTON_TYPE.circle}
						icon={buttonConfig.icon}
						onClick={buttonConfig.onClick}
					>
						{buttonConfig.name}
					</Button>
				))}
			</div>
		</div>
	);
}
