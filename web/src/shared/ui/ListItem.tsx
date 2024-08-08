import {cloneElement, ReactElement, ReactNode} from 'react';

import {APP_ICON} from '@shared/ui/index.ts';

import {cn} from '@shared/lib';

type Props = {
	mask?: ReactElement;
	name: ReactNode;
	description?: ReactNode;
	checked?: boolean;
	amount?: string;
};

/**
 * Rename or move into button, used in list, in select just for same composition
 */

export function ListItem(props: Props) {
	const {name, description, checked, amount, mask} = props;

	return (
		<div className={cn('flex', checked && 'bg-secondary-violet')}>
			<div className='relative my-0.5 mr-4 h-10 w-10 flex-shrink-0 rounded-full'>
				{mask ? (
					cloneElement(mask, {style: {height: '100%', borderRadius: '50%'}})
				) : (
					<div className='h-full rounded-full bg-primary-grey' />
				)}

				{checked && (
					<div
						className={cn(
							'shadow-[0_0_0_2px_white_inset]',
							'absolute -bottom-1 -right-1 flex h-5 w-5 items-center justify-center rounded-full bg-primary-violet text-white',
						)}
					>
						<div className='h-3 w-3'>{APP_ICON.check}</div>
					</div>
				)}
			</div>

			<div className='self-stretch'>
				<div className='font-medium'>{name}</div>
				{description && <div className='text-sm font-light text-primary-grey'>{description}</div>}
			</div>

			{/*Not working, can add to wraper justify-between and 2 child div */}
			{amount && <div className='justify-self-end'>{amount}</div>}
		</div>
	);
}
