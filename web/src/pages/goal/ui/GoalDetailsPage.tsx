import {Box, Button, Card, ListItem, PageHeader} from '@shared/ui';

import {APP_PATH, APP_TEXT} from '@shared/config';

const transactions = [
	{name: 'Add money', description: 'Today, 15:45', amount: '+37 $'},
	{name: 'Withdraw', description: '03.02.2023, 17:15', amount: '-47 $'},
	{name: 'Ferrari', description: '01.02.2023, 11:18', amount: '+397 $'},
];

export function GoalDetailsPage() {
	return (
		<>
			<div role='image-wrapper' className='flex h-[290px] flex-col justify-between bg-secondary-grey'>
				<PageHeader title='Mustang' backPath={APP_PATH.root} />

				<div className='mb-4 flex justify-between px-6'>
					<div className='h-12 w-12 rounded-full bg-primary-grey' />
					<div className='h-12 w-12 rounded-full bg-primary-grey' />
					<div className='h-12 w-12 rounded-full bg-primary-grey' />
					<div className='h-12 w-12 rounded-full bg-primary-grey' />
				</div>
			</div>

			<Box withBaseHorizontal withMediumTop>
				<Card withBaseHorizontal>
					<Box withBaseTop>
						<div className='flex justify-between'>
							<div className='text-sm font-medium'>Saved 50%</div>
							<div className='text-sm'>12 500 / 25 000 $</div>
						</div>
					</Box>
					<Box withBaseTop>
						<div className='h-1 w-full rounded-2xl bg-primary-grey' />
					</Box>
					<div className='flex justify-between py-4'>
						<div className='text-sm text-primary-grey'>Deadline: 15 june 2024</div>
						<div className='text-sm text-primary-grey'>127d left</div>
					</div>
				</Card>
			</Box>

			<Box withMediumTop withTitleBottom withBaseHorizontal>
				<div className='flex items-end justify-between'>
					<div className='font-semibold'>{APP_TEXT.transactions}</div>
					<div className='text-primary-violet'>{APP_TEXT.seeAll}</div>
				</div>
			</Box>
			<Box withBaseHorizontal>
				<Card>
					{transactions.map((row, index) => (
						<Button key={index} onClick={() => {}}>
							<ListItem name={row.name} description={row.description} amount={row.amount} />
						</Button>
					))}
				</Card>
			</Box>
		</>
	);
}
