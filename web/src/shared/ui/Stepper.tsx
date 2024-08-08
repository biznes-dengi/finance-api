import {ReactNode} from 'react';

import {Box} from '@shared/ui/index.ts';

import {cn} from '@shared/lib';

type Props = {
	activeStepIndex: number;
	steps: ReactNode[];
};

export function Stepper(props: Props) {
	const {activeStepIndex, steps} = props;

	return (
		<>
			<Box withMediumVertical className={cn('flex justify-center')}>
				{Array.from({length: steps.length}).map((item, index) => (
					<div
						key={'' + item + index}
						className={cn(
							'mr-1 h-0.5 w-10 rounded-2xl bg-primary-grey shadow-light-grey last:mr-0',
							index <= activeStepIndex && 'bg-primary-violet',
						)}
					/>
				))}
			</Box>

			{steps[activeStepIndex]}
		</>
	);
}
