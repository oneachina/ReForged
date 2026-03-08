package net.neoforged.neoforge.common;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class NeoForgeMod {
    private static boolean milkFluidEnabled = false;

    /**
     * Stub: NeoForge method called by mods (e.g. Create) to enable milk as a fluid.
     * On Forge this is a no-op since Forge handles milk its own way.
     */
    public static void enableMilkFluid() {
        milkFluidEnabled = true;
    }

    // ── FluidType holders bridged from Forge's ForgeMod ──────────────────

    public static final Holder<FluidType> EMPTY_TYPE = lazyHolder(ForgeMod.EMPTY_TYPE);
    public static final Holder<FluidType> WATER_TYPE = lazyHolder(ForgeMod.WATER_TYPE);
    public static final Holder<FluidType> LAVA_TYPE  = lazyHolder(ForgeMod.LAVA_TYPE);

    /**
     * Creates a lazy {@link Holder} that resolves from a Forge {@link RegistryObject}.
     * At class-init time the RegistryObject may not yet be populated, so we defer
     * resolution until first access via {@link #value()}.
     */
    @SuppressWarnings("unchecked")
    private static <T> Holder<T> lazyHolder(RegistryObject<T> registryObject) {
        return new Holder<T>() {
            private volatile Holder<T> delegate;

            private Holder<T> resolve() {
                if (delegate == null) {
                    synchronized (this) {
                        if (delegate == null) {
                            delegate = registryObject.getHolder()
                                    .orElseGet(() -> Holder.direct(registryObject.get()));
                        }
                    }
                }
                return delegate;
            }

            @Override public T value() { return resolve().value(); }
            @Override public boolean isBound() { return resolve().isBound(); }
            @Override public boolean is(ResourceLocation loc) { return resolve().is(loc); }
            @Override public boolean is(ResourceKey<T> key) { return resolve().is(key); }
            @Override public boolean is(Predicate<ResourceKey<T>> pred) { return resolve().is(pred); }
            @Override public boolean is(TagKey<T> tag) { return resolve().is(tag); }
            @Override public boolean is(Holder<T> other) { return resolve().is(other); }
            @Override public Stream<TagKey<T>> tags() { return resolve().tags(); }
            @Override public Either<ResourceKey<T>, T> unwrap() { return resolve().unwrap(); }
            @Override public Optional<ResourceKey<T>> unwrapKey() { return resolve().unwrapKey(); }
            @Override public Kind kind() { return resolve().kind(); }
            @Override public boolean canSerializeIn(HolderOwner<T> owner) { return resolve().canSerializeIn(owner); }
        };
    }
}
