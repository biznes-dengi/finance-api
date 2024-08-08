import {APP_PATH} from '@shared/config';

import {BaseLayout, HomePageLayout} from '@pages/ui';
import {GoalCreatePage} from '../ui/GoalCreatePage.tsx';
import {GoalDetailsPage} from '../ui/GoalDetailsPage.tsx';
import {GoalListPage} from '../ui/GoalListPage.tsx';

import {withRouteGuard} from '@shared/lib';

const goalCreateRoute = {
	path: APP_PATH.createGoal,
	element: withRouteGuard({page: <GoalCreatePage />}),
};

const goalDetailsRoute = {
	path: APP_PATH.goalDetails,
	element: withRouteGuard({page: <GoalDetailsPage />}),
};

const goalListRoute = {
	path: APP_PATH.root,
	element: withRouteGuard({page: <GoalListPage />}),
};

export const goalRoutes = [
	{element: <HomePageLayout />, children: [goalListRoute]},
	{element: <BaseLayout />, children: [goalCreateRoute, goalDetailsRoute]},
];
