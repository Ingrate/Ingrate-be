package dompoo.Ingrate.ingredient;

import dompoo.Ingrate.config.enums.Unit;
import dompoo.Ingrate.ingredient.dto.IngredientAddRequest;
import dompoo.Ingrate.ingredient.dto.IngredientDetailResponse;
import dompoo.Ingrate.ingredient.dto.IngredientResponse;
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

    public void addIngredient(Long memberId, IngredientAddRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Ingredient ingredient = ingredientRepository.save(Ingredient.builder()
                .name(request.getName())
                .cost(request.getCost())
                .amount(request.getAmount())
                .unit(Unit.valueOf(request.getUnit()))
                .memo(request.getMemo())
                .date(LocalDate.now())
                .build());

        ingredient.setMember(member);
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 식재료입니다."));

        if (!ingredient.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 식재료가 아닙니다.");
        }

        return new IngredientDetailResponse(ingredient);
    }
}
