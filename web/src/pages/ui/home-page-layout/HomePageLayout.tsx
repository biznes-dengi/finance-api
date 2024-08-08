import {Outlet} from 'react-router-dom';

import {HomePageHeader} from './HomePageHeader.tsx';
import {HomePageNavbar} from './HomePageNavbar.tsx';

import {cn} from '@shared/lib';

export function HomePageLayout() {
	const isDesktop = false;

	return (
		<div
			className={cn('flex min-h-screen justify-between bg-[#F7F7F7]', !isDesktop && 'p-4', isDesktop && 'px-6 py-8')}
		>
			{isDesktop && (
				<div role='app-sidebar' className='w-52'>
					<HomePageNavbar />
				</div>
			)}

			<div role='app-content' className={cn('flex flex-col', isDesktop ? 'w-[80%]' : 'w-full')}>
				<HomePageHeader />
				<Outlet />
			</div>
		</div>
	);
}
