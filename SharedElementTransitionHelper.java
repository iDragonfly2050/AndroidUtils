package com.bignerdranch.android.photogallery;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SharedElementCallback;
import android.transition.Transition;
import android.view.View;
import android.view.ViewTreeObserver;

public class SharedElementTransitionHelper {
	public static void pauseEnterTranstion(Activity activity) {
		activity.postponeEnterTransition();
	}

	public static void pauseEnterTranstion(FragmentActivity activity) {
		activity.supportPostponeEnterTransition();
	}

	public static void startEnterTranstionWhenViewIsReady(Activity activity, View v) {
		v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				v.getViewTreeObserver().removeOnPreDrawListener(this);
				activity.startPostponedEnterTransition();
				return true;
			}
		});
	}

	public static void startEnterTranstionWhenViewIsReady(FragmentActivity activity, View v) {
		v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				v.getViewTreeObserver().removeOnPreDrawListener(this);
				activity.supportStartPostponedEnterTransition();
				return true;
			}
		});
	}

	public static void setExitSharedElementCallbackOnce(FragmentActivity activity, SharedElementCallback callback) {
		setExitSharedElementCallback(activity, callback);
		activity.getWindow().getSharedElementExitTransition().addListener(new TransitionListenerAdapter() {
			@Override
			public void onTransitionEnd(@NonNull Transition transition) {
				activity.setExitSharedElementCallback(new SharedElementCallback() {});
				super.onTransitionEnd(transition);
			}

			@Override
			public void onTransitionCancel(@NonNull Transition transition) {
				activity.setExitSharedElementCallback(new SharedElementCallback() {});
				super.onTransitionCancel(transition);
			}
		});
	}

	public static void setExitSharedElementCallback(FragmentActivity activity, SharedElementCallback callback) {
		activity.setExitSharedElementCallback(callback);
	}

	public static void setEnterSharedElementCallbackOnce(FragmentActivity activity, SharedElementCallback callback) {
		setEnterSharedElementCallback(activity, callback);
		activity.getWindow().getSharedElementEnterTransition().addListener(new TransitionListenerAdapter() {
			@Override
			public void onTransitionEnd(@NonNull Transition transition) {
				activity.setEnterSharedElementCallback(new SharedElementCallback() {});
				super.onTransitionEnd(transition);
			}

			@Override
			public void onTransitionCancel(@NonNull Transition transition) {
				activity.setEnterSharedElementCallback(new SharedElementCallback() {});
				super.onTransitionCancel(transition);
			}
		});
	}

	public static void setEnterSharedElementCallback(FragmentActivity activity, SharedElementCallback callback) {
		activity.setEnterSharedElementCallback(callback);
	}

	public static void setExitSharedElementCallbackOnce(Activity activity, android.app.SharedElementCallback callback) {
		setExitSharedElementCallback(activity, callback);
		activity.getWindow().getSharedElementExitTransition().addListener(new TransitionListenerAdapter() {
			@Override
			public void onTransitionEnd(@NonNull Transition transition) {
				activity.setExitSharedElementCallback(new android.app.SharedElementCallback() {});
				super.onTransitionEnd(transition);
			}

			@Override
			public void onTransitionCancel(@NonNull Transition transition) {
				activity.setExitSharedElementCallback(new android.app.SharedElementCallback() {});
				super.onTransitionCancel(transition);
			}
		});
	}

	public static void setExitSharedElementCallback(Activity activity, android.app.SharedElementCallback callback) {
		activity.setExitSharedElementCallback(callback);
	}

	public static void setEnterSharedElementCallbackOnce(Activity activity, android.app.SharedElementCallback callback) {
		setEnterSharedElementCallback(activity, callback);
		activity.getWindow().getSharedElementEnterTransition().addListener(new TransitionListenerAdapter() {
			@Override
			public void onTransitionEnd(@NonNull Transition transition) {
				activity.setEnterSharedElementCallback(new android.app.SharedElementCallback() {});
				super.onTransitionEnd(transition);
			}

			@Override
			public void onTransitionCancel(@NonNull Transition transition) {
				activity.setEnterSharedElementCallback(new android.app.SharedElementCallback() {});
				super.onTransitionCancel(transition);
			}
		});
	}

	public static void setEnterSharedElementCallback(Activity activity, android.app.SharedElementCallback callback) {
		activity.setEnterSharedElementCallback(callback);
	}
}
