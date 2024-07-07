package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.IngredientUnit.IngredientUnitService;
import dompoo.Ingrate.config.enums.Unit;
import dompoo.Ingrate.exception.IngredientNotFound;
import dompoo.Ingrate.exception.MemberNotFound;
import dompoo.Ingrate.exception.NotMyIngredient;
import dompoo.Ingrate.exception.UnitNotFound;
import dompoo.Ingrate.ingredient.dto.*;
import dompoo.Ingrate.member.Member;
import dompoo.Ingrate.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final MemberRepository memberRepository;
    private final IngredientUnitService unitService;

    public IngredientRateResponse rateIngredient(IngredientRateRequest request) {
        if (!unitService.unitExistCheck(request.getName(), Unit.valueOf(request.getUnit()))) {
            throw new UnitNotFound();
        }

        // 종류와 단위가 같은 식재료들 (모집단)
        List<Ingredient> allList = ingredientRepository.findAllByNameAndUnit(request.getName(), Unit.valueOf(request.getUnit()));

        // 모집단의 개수
        int volume = allList.size();
        // 가성비가 더 좋은 식재료의 개수
        int betterVolume = allList.stream()
                .filter(in -> (in.getAmount() / in.getCost()) > (request.getAmount() / request.getCost()))
                .toList().size();

        // 가성비가 더 좋은 식재료의 개수 / 모집단의 개수 ==> 레이팅
        return IngredientRateResponse.builder()
                .rate((float) (betterVolume / volume) * 100)
                .volume(volume)
                .build();
    }

    public void addIngredient(Long memberId, IngredientAddRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);

        if (!unitService.unitExistCheck(request.getName(), Unit.valueOf(request.getEnumUnit()))) {
            throw new UnitNotFound();
        }

        Ingredient ingredient = ingredientRepository.save(Ingredient.builder()
                .name(request.getName())
                .cost(request.getCost())
                .amount(request.getAmount())
                .unit(Unit.valueOf(request.getEnumUnit()))
                .memo(request.getMemo())
                .date(LocalDate.now())
                .member(member)
                .build());

        member.addPost(1);
        member.addPoint(1);
    }

    public List<IngredientResponse> getMyIngredient(Long memberId) {
        return ingredientRepository.findAll().stream()
                .filter(ingredient -> ingredient.getMember().getId().equals(memberId))
                .map(IngredientResponse::new)
                .toList();
    }

    public IngredientDetailResponse getMyIngredientDetail(Long memberId, Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(IngredientNotFound::new);

        if (!ingredient.getMember().getId().equals(memberId)) {
            throw new NotMyIngredient();
        }

        return new IngredientDetailResponse(ingredient);
    }

    public IngredientDetailResponse editMyIngredient(Long memberId, Long ingredientId, IngredientEditRequest request) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(IngredientNotFound::new);

        if (!ingredient.getMember().getId().equals(memberId)) {
            throw new NotMyIngredient();
        }

        if (!unitService.unitExistCheck(request.getName(), Unit.valueOf(request.getEnumUnit()))) {
            throw new UnitNotFound();
        }

        ingredient.setName(request.getName());
        ingredient.setCost(request.getCost());
        ingredient.setAmount(request.getAmount());
        ingredient.setUnit(Unit.valueOf(request.getEnumUnit()));
        ingredient.setMemo(request.getMemo());

        return new IngredientDetailResponse(ingredient);
    }

    public void deleteMyIngredient(Long memberId, Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(IngredientNotFound::new);

        if (!ingredient.getMember().getId().equals(memberId)) {
            throw new NotMyIngredient();
        }

        ingredientRepository.delete(ingredient);
    }

    public List<IngredientResponse> getAllIngredient() {
        return ingredientRepository.findAll().stream()
                .map(IngredientResponse::new)
                .toList();
    }

    public IngredientAdminDetailResponse getIngredientDetail(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(IngredientNotFound::new);

        return new IngredientAdminDetailResponse(ingredient);
    }

    public IngredientAdminDetailResponse editIngredient(Long ingredientId, IngredientEditRequest request) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(IngredientNotFound::new);

        if (!unitService.unitExistCheck(request.getName(), Unit.valueOf(request.getEnumUnit()))) {
            throw new UnitNotFound();
        }

        ingredient.setName(request.getName());
        ingredient.setCost(request.getCost());
        ingredient.setAmount(request.getAmount());
        ingredient.setUnit(Unit.valueOf(request.getEnumUnit()));
        ingredient.setMemo(request.getMemo());

        return new IngredientAdminDetailResponse(ingredient);
    }

    public void deleteIngredient(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(IngredientNotFound::new);

        ingredientRepository.delete(ingredient);
    }
}
